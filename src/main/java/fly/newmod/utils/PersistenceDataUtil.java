package fly.newmod.utils;

//WIP
public class PersistenceDataUtil {
    /*public static final PersistentDataType<Component, Component> COMPONENT = new PersistentDataType<Component, Component>() {
        {
            try {
                CraftPersistentDataTypeRegistry registry = new CraftPersistentDataTypeRegistry();

                Class clazz = Class.forName("org.bukkit.craftbukkit.v1_16_R3.persistence.CraftPersistentDataTypeRegistry.TagAdapter");

                Constructor constructor = clazz.getConstructor(Class.class, Class.class, Function.class, Function.class);

                Object adapter = constructor.newInstance(Component.class, NBTTagCompound.class, (Function<Component, NBTTagCompound>)(x) -> {
                    NBTTagCompound compound = new NBTTagCompound();

                    TextComponent component = (TextComponent) x;

                    compound.setString("text", component.content());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public @NotNull Class<Component> getPrimitiveType() {
            return Component.class;
        }

        @Override
        public @NotNull Class<Component> getComplexType() {
            return Component.class;
        }

        @NotNull
        @Override
        public Component toPrimitive(@NotNull Component component, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return component;
        }

        @NotNull
        @Override
        public Component fromPrimitive(@NotNull Component component, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return component;
        }
    };*/
    //public static void getOrDefault()
}

