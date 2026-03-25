package com.stalemated.mcdloretoggler.mixin.client;

import chronosacaria.mcda.items.ArmorSetItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.stalemated.mcdloretoggler.config.MCDLoreTogglerConfig;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import java.util.List;

@Mixin(ArmorSetItem.class)
public class ArmorSetItemMixin {

    @Redirect(
            method = "appendTooltip",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")
    )
    private boolean interceptAddTooltip(List<Object> instance, Object textObj) {
        if (textObj instanceof Text text) {

            boolean isArmorEffect = false;

            if (text.getContent() instanceof TranslatableTextContent translatable) {
                isArmorEffect = translatable.getKey().contains("effect.tooltip");
            }

            if (isArmorEffect) {
                if (!MCDLoreTogglerConfig.showMCDAEffectLore) {
                    return false;
                }
            } else {
                if (!MCDLoreTogglerConfig.showMCDAFlavorLore) {
                    return false;
                }
            }
        }

        return instance.add(textObj);
    }
}