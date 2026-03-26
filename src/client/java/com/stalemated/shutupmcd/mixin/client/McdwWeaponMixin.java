package com.stalemated.shutupmcd.mixin.client;

import chronosacaria.mcdw.api.util.CleanlinessHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import java.util.List;
import com.stalemated.shutupmcd.config.ShutUpMCDConfig;

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

                if (translatable.getKey().contentEquals("tooltip_info_item.mcdw.gap") && !ShutUpMCDConfig.showMCDWEffectLore) return false;
            }

            if (isWeaponEffect) {
                if (!ShutUpMCDConfig.showMCDWEffectLore) return false;
            } else {
                if (!ShutUpMCDConfig.showMCDWFlavorLore) return false;
            }
        }

        return instance.add(textObj);
    }
}