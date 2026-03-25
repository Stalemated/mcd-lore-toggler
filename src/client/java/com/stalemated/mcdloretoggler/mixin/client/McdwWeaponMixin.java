package com.stalemated.mcdloretoggler.mixin.client;

import chronosacaria.mcdw.api.util.CleanlinessHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.stalemated.mcdloretoggler.config.MCDLoreTogglerConfig;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import java.util.List;

@Mixin(value = {CleanlinessHelper.class})
public class McdwWeaponMixin {

    @Redirect(
            method = "mcdw$tooltipHelper",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
            require = 0
    )
    private static boolean interceptAddTooltip(List<Object> instance, Object textObj) {
        if (textObj instanceof Text text) {

            boolean isWeaponEffect = false;

            if (text.getContent() instanceof TranslatableTextContent translatable) {
                isWeaponEffect = translatable.getKey().contentEquals("tooltip_note_item.mcdw.dualwield") || translatable.getKey().contains("tooltip_note_item.mcdw.longbow") || translatable.getKey().contentEquals("tooltip_note_item.mcdw.shortbow");
                if (translatable.getKey().contentEquals("tooltip_info_item.mcdw.gap") && !MCDLoreTogglerConfig.showMCDWEffectLore) {
                    return false;
                }
            }

            if (isWeaponEffect) {
                if (!MCDLoreTogglerConfig.showMCDWEffectLore) {
                    return false;
                }
            } else {
                if (!MCDLoreTogglerConfig.showMCDWFlavorLore) {
                    return false;
                }
            }
        }

        return instance.add(textObj);
    }
}