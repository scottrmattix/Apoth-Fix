package dev.stick.apoth_fix.mixin;
import dev.stick.apoth_fix.ApothFix;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import shadows.apotheosis.adventure.loot.LootRarity;

import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.util.random.WeightedRandom;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(value = LootRarity.class, remap = false)
public abstract class LootRarityMixin {

    @Final
    @Shadow
    public static List<LootRarity> LIST;
    @Final
    @Shadow
    public static LootRarity COMMON;
    /**
     * @author stick
     * @reason fixes problem where the optional could not have a value
     */
    @Overwrite
    public static LootRarity random(RandomSource rand, float luck, @Nullable LootRarity min, @Nullable LootRarity max) {
        List<Wrapper<LootRarity>> list = LIST.stream().filter(r -> r.clamp(min, max) == r).map(r -> r.<LootRarity>wrap(luck)).toList();
        return WeightedRandom.getRandomItem(rand, list).map(Wrapper::getData).orElse(LootRarity.COMMON);
    }
}
