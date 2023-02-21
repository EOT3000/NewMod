package fly.newmod.armor.meta;

import fly.newmod.parent.api.item.meta.ModItemMeta;
import fly.newmod.armor.type.damage.DamageType;

import java.util.List;

public interface CustomArmorMeta extends ModItemMeta {
    List<DamageType> getFlags();

    int getValue(DamageType flag);

    void setFlag(DamageType flag, int i);
}
