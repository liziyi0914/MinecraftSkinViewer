package com.liziyi0914.lnmcl.msv;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.transform.Rotate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

public class SkinView extends AnchorPane {

    @Getter
    public Block head, body, left_hand, right_hand, left_leg, right_leg;

    ArrayList<Node> elements = new ArrayList<>();

    public PerspectiveCamera camera;
    AmbientLight light;

    AnchorPane group;

    Point3D xAxis = Rotate.X_AXIS;
    Point3D yAxis = Rotate.Y_AXIS;
    Point3D zAxis = Rotate.Z_AXIS;

    Block[] headGroup, bodyGroup, legGroup;
    Block[][] modalGroup;
    Block[] bodyGroup_left, bodyGroup_right, legGroup_left, legGroup_right;

    public SkinView(Image skin,Model model) {
        this(skin,model,skin.getHeight()<64);
    }

    public SkinView(Image skin,Model model,boolean oldType) {
        super();

        if (oldType) {
            skin = SwingFXUtils.toFXImage(Utils.updateSkin(SwingFXUtils.fromFXImage(skin,null)),null);
        }

        group = new AnchorPane();

        camera = new PerspectiveCamera(true);
        camera.setFarClip(1000);
        camera.setNearClip(0.1);
        camera.setTranslateZ(-600);

        light = new AmbientLight();

        head = new Block(skin, model.HEAD, 8);
        body = new Block(skin,model.BODY,8);
        left_leg = new Block(skin,model.LEG_L, 8);
        right_leg = new Block(skin,model.LEG_R,8);
        left_hand = new Block(skin,model.HAND_L,8);
        right_hand = new Block(skin,model.HAND_R, 8);

        headGroup = new Block[]{head};
        bodyGroup_left = new Block[]{right_hand, body, left_hand};
        bodyGroup_right = new Block[]{left_hand, body, right_hand};
        legGroup_left = new Block[]{right_leg, left_leg};
        legGroup_right = new Block[]{left_leg, right_leg};

        bodyGroup = bodyGroup_left;
        legGroup = legGroup_left;

        modalGroup = new Block[][]{legGroup, bodyGroup, headGroup};

        elements.addAll(Arrays.asList(right_leg, left_leg, right_hand, body, head, left_hand));

        head.setTranslateX(model.HEAD_LOCATION.getX());
        head.setTranslateY(model.HEAD_LOCATION.getY());
        head.setTranslateZ(model.HEAD_LOCATION.getZ());

        body.setTranslateX(model.BODY_LOCATION.getX());
        body.setTranslateY(model.BODY_LOCATION.getY());
        body.setTranslateZ(model.BODY_LOCATION.getZ());

        left_hand.setTranslateX(model.HAND_L_LOCATION.getX());
        left_hand.setTranslateY(model.HAND_L_LOCATION.getY());
        left_hand.setTranslateZ(model.HAND_L_LOCATION.getZ());

        right_hand.setTranslateX(model.HAND_R_LOCATION.getX());
        right_hand.setTranslateY(model.HAND_R_LOCATION.getY());
        right_hand.setTranslateZ(model.HAND_R_LOCATION.getZ());

        left_leg.setTranslateX(model.LEG_L_LOCATION.getX());
        left_leg.setTranslateY(model.LEG_L_LOCATION.getY());
        left_leg.setTranslateZ(model.LEG_L_LOCATION.getZ());

        right_leg.setTranslateX(model.LEG_R_LOCATION.getX());
        right_leg.setTranslateY(model.LEG_R_LOCATION.getY());
        right_leg.setTranslateZ(model.LEG_R_LOCATION.getZ());

        head.getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS));
        body.getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS));
        right_leg.getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS));
        left_leg.getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS));
        right_hand.getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS));
        left_hand.getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS));

        group.getChildren().setAll(light);
        group.getChildren().addAll(flattenBlocks(modalGroup));
        group.setBackground(Background.EMPTY);

        SubScene subScene = new SubScene(group, 200, 200, false, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.widthProperty().bind(widthProperty());
        subScene.heightProperty().bind(heightProperty());

        getChildren().add(subScene);
    }

    public void rotate(Rotate... rotates) {
        Arrays.asList(rotates).forEach(rotate -> {
            double angle = rotate.getAngle();
            angle = (Math.abs(angle) % 360) * Math.signum(angle);
            angle = Math.abs(angle) <= 180 ? angle : (360 - Math.abs(angle)) * Math.signum(angle) * (-1);
            double[] tmp;
            if (rotate.getAxis().equals(Rotate.X_AXIS)) {
                tmp = Utils.rotate(new double[]{zAxis.getZ(), zAxis.getY()}, angle);
                zAxis = new Point3D(zAxis.getX(), -tmp[1], tmp[0]);
                tmp = Utils.rotate(new double[]{yAxis.getY(), yAxis.getZ()}, angle);
                yAxis = new Point3D(yAxis.getX(), tmp[0], tmp[1]);
            } else if (rotate.getAxis().equals(Rotate.Y_AXIS)) {
                tmp = Utils.rotate(new double[]{zAxis.getZ(), zAxis.getX()}, angle);
                zAxis = new Point3D(tmp[1], zAxis.getY(), tmp[0]);
                tmp = Utils.rotate(new double[]{xAxis.getX(), xAxis.getZ()}, angle);
                xAxis = new Point3D(tmp[0], xAxis.getY(), tmp[1]);
            }
        });
        group.getTransforms().setAll(rotates);
        elements.forEach(node -> {
            Block block = (Block) node;
            block.calcVisibleSides(rotates);
        });
        if (!head.leftVisible) {
            bodyGroup = bodyGroup_left;
            legGroup = legGroup_left;
        } else {
            bodyGroup = bodyGroup_right;
            legGroup = legGroup_right;
        }
        if (head.topVisible) {
            modalGroup = new Block[][]{legGroup, bodyGroup, headGroup};
        } else {
            modalGroup = new Block[][]{headGroup, bodyGroup, legGroup};
        }
        group.getChildren().setAll(light);
        group.getChildren().addAll(flattenBlocks(modalGroup));
    }

    Block[] flattenBlocks(Block[][] blocks) {
        ArrayList<Block> list = new ArrayList<>();
        for (Block[] tmp : blocks) {
            list.addAll(Arrays.asList(tmp));
        }
        return list.toArray(new Block[0]);
    }

}
