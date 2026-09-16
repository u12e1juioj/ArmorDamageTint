package xkzuto.armordamagetint.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xkzuto.armordamagetint.ArmorDamageTintClient;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> {

	// The descriptor is spelled out explicitly because the class also carries a
	// bridge method with the same name (taking EntityRenderState).
	private static final String SUBMIT = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V";

	@Inject(method = SUBMIT, at = @At("HEAD"))
	private void onSubmitArmor(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S state, float limbSwing, float limbSwingAmount, CallbackInfo ci) {
		ArmorDamageTintClient.setRenderingPlayerArmor(true);
		ArmorDamageTintClient.setPlayerHasRedOverlay(state.hasRedOverlay);
	}

	@Inject(method = SUBMIT, at = @At("RETURN"))
	private void afterSubmitArmor(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S state, float limbSwing, float limbSwingAmount, CallbackInfo ci) {
		ArmorDamageTintClient.setRenderingPlayerArmor(false);
		ArmorDamageTintClient.setPlayerHasRedOverlay(false);
	}
}
