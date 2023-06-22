package me.fly.newmod.metals;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.fly.newmod.api.item.texture.SkullTexture;
import org.bukkit.Bukkit;

import java.util.UUID;

public enum MetalTextures {
    FILLED_ORE_SPONGE {
        private final SkullTexture rawTexture;

        {
            // Skin 6f3da0ea generated on Feb 19, 2022 12:03:43 PM via MineSkin.org - https://minesk.in/6f3da0eac40d4243a38739c75e7f1be7
            PlayerProfile skin6f3da0ea = Bukkit.createProfile(UUID.fromString("7d1d77b2-0abf-42d0-9296-b129a9967cbc"), "skin6f3da0ea");
            skin6f3da0ea.setProperty(new ProfileProperty("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY0NTI5MDIyMjk4NiwKICAicHJvZmlsZUlkIiA6ICIxYzdmNmQ3NDJjNjU0ZDZhYTZlMjU3NTM1MTE3MWM3NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBbHlzYWFLb2JlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhlZDc1NjBhYTlhMWM5ZDc2ZWMxMDQ1N2UyNmNmZWUwMjQ3ODQ4ZWUzYzU2MGUxY2U1NThjNzA3NzU3OWU3M2UiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "PV0tcd3j2M1PzYbbo3etjnDrGN2vmqqzZ3DaPVBJzP4cQpHWLGxncxd7zcLwVFKgKvVl02FCrYWauiZ3ktKqNUas7ayHACdHadoLp/oSXlpSCtZI+ywfTqjmlqbINxFHMKe/ypa1Yu17rFgo0GuPWk48KsDyTrNIqLsLkmN8Ojbp6JsfJiy9QuNFxL4ZHePQnlJ1ui2rZ9Z88w+VF/7lqOURmwJOp80JRLGWOdHP/csQ0TAVhHwbt34d36tqbZCqq0F0QVJzZJUVydaAe8vZ8tfSY7bUvXKR8iK+qKLKvcLVO9LzuxM8/NY3AiZeCQkYWTtI9u/afQ986NXsbxdGB7Us7EDmoQjkaBpG0dI+zjGg8jmCxxtnwF6M9aqjg+vz1uVLXWB8eE4nDuRZKeUAW6UmXIzYWO3uvS/V+xbBtE3e7rVgE2eANAUhf58lp62tSK0FfPD0PHFfYotqsKL8HroL/7xFjhfMt68edAIeMuXqdq+aPaAQJyWZDcaBZij54c4Q8HukmxEsP+TpVYCjjWXDA+lQJ3BuU5HiXtqixF/XHsemvAmicpDQ11o3MjmzJfKUIBuJqjNi1CZyXV6suBED6S3PZcb0BsUa55t5d8wRYUIYHAsOmUVgaoVMlAiAxNBZw86HV1IuynshkBVZsDWj0aomGuK4NkiEL0/bX3c="));

            this.rawTexture = new SkullTexture(skin6f3da0ea);
        }

        @Override
        public SkullTexture getRawTexture() {
            return rawTexture;
        }
    };

    public abstract SkullTexture getRawTexture();
}
