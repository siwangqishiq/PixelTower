
package com.thegreystudios.pixeltower.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.blocks.*;
import com.thegreystudios.pixeltower.status.GameStatus;

public class BlocksRenderer
{

    public BlocksRenderer(OrthographicCamera camera, TextureAtlas atlas, SpriteBatch batch)
    {
        normalLeft = new TextureRegion[4];
        normalMiddle = new TextureRegion[7];
        normalRight = new TextureRegion[4];
        labLeft = new TextureRegion[2];
        labMiddle = new TextureRegion[3];
        labRight = new TextureRegion[2];
        biblioLeft = new TextureRegion[2];
        biblioMiddle = new TextureRegion[3];
        biblioRight = new TextureRegion[2];
        ateriumLeft = new TextureRegion[2];
        ateriumMiddle = new TextureRegion[3];
        ateriumRight = new TextureRegion[2];
        stairs = new TextureRegion[1];
        walls = new TextureRegion[2];
        balconyLeft = new TextureRegion[2];
        balconyRight = new TextureRegion[2];
        studLeft = new TextureRegion[2];
        studRight = new TextureRegion[2];
        this.camera = camera;
        this.batch = batch;
        loadAssets(atlas);
    }

    private void loadAssets(TextureAtlas atlas)
    {
        normalLeft[0] = atlas.findRegion("normalLeftRoom01");
        normalLeft[1] = atlas.findRegion("normalLeftRoom02");
        normalLeft[2] = atlas.findRegion("normalLeftRoom03");
        normalLeft[3] = atlas.findRegion("normalLeftRoom04");
        normalMiddle[0] = atlas.findRegion("normalMiddleRoom01");
        normalMiddle[1] = atlas.findRegion("normalMiddleRoom02");
        normalMiddle[2] = atlas.findRegion("normalMiddleRoom03");
        normalMiddle[3] = atlas.findRegion("normalStairsRoom01");
        normalMiddle[4] = atlas.findRegion("normalStairsRoom02");
        normalMiddle[5] = atlas.findRegion("normalMiddleRoom04");
        normalMiddle[6] = atlas.findRegion("normalMiddleRoom05");
        normalRight[0] = atlas.findRegion("normalRightRoom01");
        normalRight[1] = atlas.findRegion("normalRightRoom02");
        normalRight[2] = atlas.findRegion("normalRightRoom03");
        normalRight[3] = atlas.findRegion("normalRightRoom04");
        labLeft[0] = atlas.findRegion("labLeftRoom01");
        labLeft[1] = atlas.findRegion("labLeftRoom02");
        labMiddle[0] = atlas.findRegion("labMiddleRoom01");
        labMiddle[1] = atlas.findRegion("labMiddleRoom02");
        labMiddle[2] = atlas.findRegion("labMiddleRoom03");
        labRight[0] = atlas.findRegion("labRightRoom01");
        labRight[1] = atlas.findRegion("labRightRoom02");
        ateriumLeft[0] = atlas.findRegion("ateriumLeftRoom01");
        ateriumLeft[1] = atlas.findRegion("ateriumLeftRoom02");
        ateriumMiddle[0] = atlas.findRegion("ateriumMiddleRoom01");
        ateriumMiddle[1] = atlas.findRegion("ateriumMiddleRoom02");
        ateriumMiddle[2] = atlas.findRegion("ateriumMiddleRoom03");
        ateriumRight[0] = atlas.findRegion("ateriumRightRoom01");
        ateriumRight[1] = atlas.findRegion("ateriumRightRoom02");
        biblioLeft[0] = atlas.findRegion("biblioLeftRoom01");
        biblioLeft[1] = atlas.findRegion("biblioLeftRoom02");
        biblioMiddle[0] = atlas.findRegion("biblioMiddleRoom01");
        biblioMiddle[1] = atlas.findRegion("biblioMiddleRoom02");
        biblioMiddle[2] = atlas.findRegion("biblioMiddleRoom03");
        biblioRight[0] = atlas.findRegion("biblioRightRoom01");
        biblioRight[1] = atlas.findRegion("biblioRightRoom02");
        walls[0] = atlas.findRegion("outsideWall01");
        walls[1] = atlas.findRegion("outsideWall02");
        stairs[0] = atlas.findRegion("normalStairsCase01");
        balconyLeft[0] = atlas.findRegion("balconyLeft01");
        balconyLeft[1] = atlas.findRegion("balconyLeft02");
        balconyRight[0] = atlas.findRegion("balconyRight01");
        balconyRight[1] = atlas.findRegion("balconyRight02");
        studLeft[0] = atlas.findRegion("timberStudLeft01");
        studLeft[1] = atlas.findRegion("timberStudLeft02");
        studRight[0] = atlas.findRegion("timberStudRight01");
        studRight[1] = atlas.findRegion("timberStudRight02");
    }

    public void render(BlocksMap blocks)
    {
        for(int i = 0; i < blocks.blocks.size; i++)
            renderBlock((Block)blocks.blocks.get(i));

        for(int i = 0; i < blocks.queue.size; i++)
            renderBlock((Block)blocks.queue.get(i));

        for(int i = 0; i < blocks.remove.size; i++)
        {
            Block block = (Block)blocks.remove.get(i);
            batch.setColor(1.0F, 1.0F, 1.0F, Math.max(1.0F - block.explodeAlpha, 0.0F));
            renderBlock(block);
        }

        batch.setColor(Color.WHITE);
    }

    private void renderBlock(Block block)
    {
        if(block.y < GameStatus.cameraHeight - 130F || block.y > GameStatus.cameraHeight + 105F)
            return;
        if(block.group != null)
            batch.setColor(block.group.colorVariance, block.group.colorVariance, block.group.colorVariance, 1.0F);
        if(block.type == 2)
            batch.draw(normalMiddle[block.variation], block.x, block.y);
        else
        if(block.type == 1)
            batch.draw(normalLeft[block.variation], block.x, block.y);
        else
        if(block.type == 3)
            batch.draw(normalRight[block.variation], block.x, block.y);
        else
        if(block.type == 13)
            batch.draw(stairs[block.variation], block.x, block.y);
        else
        if(block.type == 7)
            batch.draw(ateriumLeft[block.variation], block.x, block.y);
        else
        if(block.type == 9)
            batch.draw(ateriumRight[block.variation], block.x, block.y);
        else
        if(block.type == 8)
            batch.draw(ateriumMiddle[block.variation], block.x, block.y);
        else
        if(block.type == 4)
            batch.draw(labLeft[block.variation], block.x, block.y);
        else
        if(block.type == 6)
            batch.draw(labRight[block.variation], block.x, block.y);
        else
        if(block.type == 5)
            batch.draw(labMiddle[block.variation], block.x, block.y);
        else
        if(block.type == 10)
            batch.draw(biblioLeft[block.variation], block.x, block.y);
        else
        if(block.type == 12)
            batch.draw(biblioRight[block.variation], block.x, block.y);
        else
        if(block.type == 11)
            batch.draw(biblioMiddle[block.variation], block.x, block.y);
        if(block.renderWallLeft)
            batch.draw(walls[block.wallVariation], block.x, block.y);
        if(block.renderWallRight)
            batch.draw(walls[block.wallVariation], block.x + 13F, block.y);
        if(block.renderBalconyLeft)
            batch.draw(balconyLeft[block.balconyVariation], block.x, block.y + 15F);
        if(block.renderBalconyRight)
            batch.draw(balconyRight[block.balconyVariation], block.x, block.y + 15F);
        if(block.renderStudLeft)
            batch.draw(studLeft[block.studVariation], block.x, block.y - 15F);
        if(block.renderStudRight)
            batch.draw(studRight[block.studVariation], block.x, block.y - 15F);
        renderedBlocksCount++;
        batch.setColor(Color.WHITE);
    }

    public void render(BlockGroup line)
    {
        if(line == null)
            return;
        for(int j = 0; j < line.blocks.size; j++)
            renderBlock((Block)line.blocks.get(j));

    }

    public void onResume()
    {
    }

    public void dispose()
    {
    }

    public static int renderedBlocksCount;
    OrthographicCamera camera;
    SpriteBatch batch;
    TextureRegion normalLeft[];
    TextureRegion normalMiddle[];
    TextureRegion normalRight[];
    TextureRegion labLeft[];
    TextureRegion labMiddle[];
    TextureRegion labRight[];
    TextureRegion biblioLeft[];
    TextureRegion biblioMiddle[];
    TextureRegion biblioRight[];
    TextureRegion ateriumLeft[];
    TextureRegion ateriumMiddle[];
    TextureRegion ateriumRight[];
    TextureRegion stairs[];
    TextureRegion walls[];
    TextureRegion balconyLeft[];
    TextureRegion balconyRight[];
    TextureRegion studLeft[];
    TextureRegion studRight[];
}
