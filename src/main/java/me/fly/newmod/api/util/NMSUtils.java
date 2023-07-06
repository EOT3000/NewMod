package me.fly.newmod.api.util;

import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.math.BigInteger;

// All util methods credit to MrPowerGamerBR
// https://www.spigotmc.org/threads/how-to-serialize-itemstack-inventory-with-attributestorage.152931/

public class NMSUtils {
    public static String toBase64(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutput = new DataOutputStream(outputStream);

            NBTTagCompound nbtTagCompoundItem = new NBTTagCompound();

            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

            nmsItem.b(nbtTagCompoundItem);

            NBTCompressedStreamTools.a(nbtTagCompoundItem, (DataOutput) dataOutput);

            return new BigInteger(1, outputStream.toByteArray()).toString(32);
        } catch (Exception e) {
            throw new RuntimeException("Idfk");
        }
    }

    public static ItemStack fromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());

            NBTTagCompound nbtTagCompoundRoot = NBTCompressedStreamTools.a((DataInput) new DataInputStream(inputStream));

            net.minecraft.world.item.ItemStack nmsItem = net.minecraft.world.item.ItemStack.a(nbtTagCompoundRoot);

            return CraftItemStack.asBukkitCopy(nmsItem);
        } catch (Exception e) {
            throw new RuntimeException("Idfk");
        }
    }

    public static String toBase64List(ItemStack[] items) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput;
        try {
            dataOutput = new BukkitObjectOutputStream(outputStream);

            // Content Size
            // Contents
            dataOutput.writeInt(items.length);

            int index = 0;
            for (ItemStack is : items) {
                if (is != null && is.getType() != Material.AIR) {
                    dataOutput.writeObject(toBase64(is));
                } else {
                    dataOutput.writeObject(null);
                }
                dataOutput.writeInt(index);
                index++;
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] fromBase64List(String items) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(items));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            int size = dataInput.readInt();

            ItemStack[] list = new ItemStack[size];
            // Read the serialized inventory
            for (int i = 0; i < size; i++) {
                Object utf = dataInput.readObject();
                int slot = dataInput.readInt();
                // I'll leave this here - Fly
                if (utf == null) { // yey²?

                } else {
                    list[slot] = fromBase64((String) utf);
                }
            }

            dataInput.close();
            return list;
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to load item stacks.", e);
        }
    }
}
