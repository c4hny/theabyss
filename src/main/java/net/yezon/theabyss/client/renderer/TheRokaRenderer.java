
package net.yezon.theabyss.client.renderer;

import net.yezon.theabyss.entity.TheRokaEntity;
import net.yezon.theabyss.client.model.ModelRokaEntityModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

public class TheRokaRenderer extends MobRenderer<TheRokaEntity, ModelRokaEntityModel<TheRokaEntity>> {
	public TheRokaRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelRokaEntityModel(context.bakeLayer(ModelRokaEntityModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new EyesLayer<TheRokaEntity, ModelRokaEntityModel<TheRokaEntity>>(this) {
			@Override
			public RenderType renderType() {
				return RenderType.eyes(new ResourceLocation("theabyss:textures/entities/rokaentitytexture_glow.png"));
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(TheRokaEntity entity) {
		return new ResourceLocation("theabyss:textures/entities/rokaentitytexture.png");
	}
}
