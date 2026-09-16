package xkzuto.armordamagetint.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xkzuto.armordamagetint.ArmorDamageTintClient;

@Mixin(EquipmentLayerRenderer.class)
public class EquipmentLayerRendererMixin {

    @Redirect(
            method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V")
    )
    private <S> void redirectSubmitModel(
            OrderedSubmitNodeCollector collector,
            Model model,
            S state,
            PoseStack poseStack,
            RenderType renderType,
            int packedLight,
            int overlay,
            int color,
            TextureAtlasSprite sprite,
            int order,
            ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
    ) {
        int finalOverlay = overlay;
        int finalColor = color;

        if (ArmorDamageTintClient.shouldApplyDamageTint()) {
            // Apply vanilla overlay for proper blending
            finalOverlay = OverlayTexture.pack(0, OverlayTexture.v(true));

            // Blend custom color with the original
            int tintColor = ArmorDamageTintClient.getTintColor();
            int tintAlpha = ARGB.alpha(tintColor);
            int tintRed = ARGB.red(tintColor);
            int tintGreen = ARGB.green(tintColor);
            int tintBlue = ARGB.blue(tintColor);

            // Get original color components
            int origAlpha = ARGB.alpha(color);
            int origRed = ARGB.red(color);
            int origGreen = ARGB.green(color);
            int origBlue = ARGB.blue(color);

            // Blend: lerp based on tint alpha
            float t = tintAlpha / 255.0f;
            int newRed = (int) (origRed * (1 - t) + tintRed * t);
            int newGreen = (int) (origGreen * (1 - t) + tintGreen * t);
            int newBlue = (int) (origBlue * (1 - t) + tintBlue * t);

            finalColor = ARGB.color(origAlpha, newRed, newGreen, newBlue);
        }

        collector.submitModel(model, state, poseStack, renderType, packedLight, finalOverlay, finalColor, sprite, order, crumblingOverlay);
    }
}
