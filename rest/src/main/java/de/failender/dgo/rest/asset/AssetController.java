package de.failender.dgo.rest.asset;

import de.failender.dgo.integration.Beans;
import de.failender.dgo.persistance.asset.AssetEntity;
import de.failender.dgo.persistance.asset.AssetRepositoryService;
import io.javalin.Context;
import io.javalin.Javalin;
import io.javalin.UploadedFile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AssetController {

    public static final String PREFIX = "api/assets/";
    public static final String UPLOAD = PREFIX + "asset/gruppe/:gruppe";

    public AssetController(Javalin app) {

        app.post(UPLOAD, this::uploadAsset);

    }


    private void uploadAsset(Context ctx) {
        UploadedFile file = ctx.uploadedFile("asset");
        long gruppe = Long.valueOf(ctx.pathParam("gruppe"));

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setName(file.getName());
        assetEntity.setGruppe(gruppe);
        AssetRepositoryService.save(assetEntity);

        File directory = new File(Beans.HELDEN_API.getCacheHandler().getRoot(), "assets");
        directory.mkdir();
        File outFile = new File(directory, assetEntity.getId() + "");
        try {
            IOUtils.copy(file.getContent(), new FileOutputStream(outFile));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
