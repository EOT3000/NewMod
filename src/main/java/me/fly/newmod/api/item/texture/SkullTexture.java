package me.fly.newmod.api.item.texture;

import com.destroystokyo.paper.profile.PlayerProfile;

public final class SkullTexture {
    private final PlayerProfile profile;

    public SkullTexture(PlayerProfile profile) {
        this.profile = profile;
    }

    public PlayerProfile getProfile() {
        return profile;
    }
}
