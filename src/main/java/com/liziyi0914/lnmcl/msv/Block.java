package com.liziyi0914.lnmcl.msv;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Block extends Group {

    public Box front, back, top, bottom, left, right;
    public ImageView front_shield, back_shield, top_shield, bottom_shield, left_shield, right_shield;

    public boolean frontVisible, backVisible, topVisible, bottomVisible, leftVisible, rightVisible;

    ArrayList<Node> surfacesA = new ArrayList<>();
    ArrayList<Node> surfacesB = new ArrayList<>();
    ArrayList<Node> surfacesC = new ArrayList<>();

    @Getter
    @Setter
    Point3D locate = Point3D.ZERO;

    public Block(Image skin,Piece[] pieces) {
        this(skin,pieces,1);
    }

    public Block(Image skin,Piece[] pieces, int scale) {
        this(skin,pieces[0],pieces[1],pieces[2],pieces[3],pieces[4],pieces[5],pieces[6],pieces[7],pieces[8],pieces[9],pieces[10],pieces[11],scale);
    }

    public Block(Image skin, Piece TOP, Piece BOTTOM, Piece LEFT, Piece RIGHT, Piece FRONT, Piece BACK,
                 Piece TOP_SHIELD, Piece BOTTOM_SHIELD, Piece LEFT_SHIELD, Piece RIGHT_SHIELD, Piece FRONT_SHIELD, Piece BACK_SHIELD) {
        this(skin, TOP, BOTTOM, LEFT, RIGHT, FRONT, BACK, TOP_SHIELD, BOTTOM_SHIELD, LEFT_SHIELD, RIGHT_SHIELD, FRONT_SHIELD, BACK_SHIELD, 1);
    }

    //上下左右前后
    public Block(Image skin,
                 Piece TOP, Piece BOTTOM, Piece LEFT, Piece RIGHT, Piece FRONT, Piece BACK,
                 Piece TOP_SHIELD, Piece BOTTOM_SHIELD, Piece LEFT_SHIELD, Piece RIGHT_SHIELD, Piece FRONT_SHIELD, Piece BACK_SHIELD,
                 int scale) {
        super();

        int w = FRONT.w;
        int h = FRONT.h;
        int d = TOP.h;

        front = new Box(FRONT.w * scale, FRONT.h * scale, .1);
        PhongMaterial frontMaterial = new PhongMaterial();
        Image frontImage = Utils.resizeImg(new WritableImage(skin.getPixelReader(), FRONT.x, FRONT.y, FRONT.w, FRONT.h), FRONT.w * scale, FRONT.h * scale);
        frontMaterial.setBumpMap(frontImage);
        frontMaterial.setDiffuseMap(frontImage);
        front.setMaterial(frontMaterial);
        front.setTranslateZ(-0.5 * d * scale);

        back = new Box(BACK.w * scale, BACK.h * scale, .1);
        PhongMaterial backMaterial = new PhongMaterial();
        Image backImage = Utils.resizeImg(new WritableImage(skin.getPixelReader(), BACK.x, BACK.y, BACK.w, BACK.h), BACK.w * scale, BACK.h * scale);
        backMaterial.setBumpMap(backImage);
        backMaterial.setDiffuseMap(backImage);
        back.setMaterial(backMaterial);
        back.setTranslateZ(0.5 * d * scale);

        top = new Box(TOP.w * scale, .1, TOP.h * scale);
        PhongMaterial topMaterial = new PhongMaterial();
        Image topImage = Utils.resizeImg(new WritableImage(skin.getPixelReader(), TOP.x, TOP.y, TOP.w, TOP.h), TOP.w * scale, TOP.h * scale);
        topMaterial.setBumpMap(topImage);
        topMaterial.setDiffuseMap(topImage);
        top.setMaterial(topMaterial);
        top.setTranslateY(-0.5 * h * scale);

        bottom = new Box(BOTTOM.w * scale, .1, BOTTOM.h * scale);
        PhongMaterial bottomMaterial = new PhongMaterial();
        Image bottomImage = Utils.resizeImg(new WritableImage(skin.getPixelReader(), BOTTOM.x, BOTTOM.y, BOTTOM.w, BOTTOM.h), BOTTOM.w * scale, BOTTOM.h * scale);
        bottomMaterial.setBumpMap(bottomImage);
        bottomMaterial.setDiffuseMap(bottomImage);
        bottom.setMaterial(bottomMaterial);
        bottom.setTranslateY(0.5 * h * scale);
        bottom.getTransforms().setAll(new Rotate(180, Rotate.Y_AXIS));

        right = new Box(.1, RIGHT.h * scale, RIGHT.w * scale);
        PhongMaterial rightMaterial = new PhongMaterial();
        Image rightImage = Utils.resizeImg(new WritableImage(skin.getPixelReader(), RIGHT.x, RIGHT.y, RIGHT.w, RIGHT.h), RIGHT.w * scale, RIGHT.h * scale);
        rightMaterial.setBumpMap(rightImage);
        rightMaterial.setDiffuseMap(rightImage);
        right.setMaterial(rightMaterial);
        right.setTranslateX(0.5 * w * scale);

        left = new Box(.1, RIGHT.h * scale, LEFT.w * scale);
        PhongMaterial leftMaterial = new PhongMaterial();
        Image leftImage = Utils.resizeImg(new WritableImage(skin.getPixelReader(), LEFT.x, LEFT.y, LEFT.w, LEFT.h), LEFT.w * scale, LEFT.h * scale);
        leftMaterial.setBumpMap(leftImage);
        leftMaterial.setDiffuseMap(leftImage);
        left.setMaterial(leftMaterial);
        left.setTranslateX(-0.5 * w * scale);

        if (FRONT_SHIELD != null) {
            front_shield = new ImageView(Utils.resizeImg(new WritableImage(skin.getPixelReader(), FRONT_SHIELD.x, FRONT_SHIELD.y, FRONT_SHIELD.w, FRONT_SHIELD.h), (FRONT_SHIELD.w + 1) * scale, (FRONT_SHIELD.h + 1) * scale));
            front_shield.setTranslateX(-0.5 * (w + 1) * scale);
            front_shield.setTranslateY(-0.5 * (h + 1) * scale);
            front_shield.setTranslateZ(-0.5 * (d + 1) * scale);
        } else {
            front_shield = new ImageView();
            front_shield.setVisible(false);
        }

        if (BACK_SHIELD != null) {
            back_shield = new ImageView(Utils.resizeImg(new WritableImage(skin.getPixelReader(), BACK_SHIELD.x, BACK_SHIELD.y, BACK_SHIELD.w, BACK_SHIELD.h), (BACK_SHIELD.w + 1) * scale, (BACK_SHIELD.h + 1) * scale));
            back_shield.setTranslateX(-0.5 * (w + 1) * scale);
            back_shield.setTranslateY(-0.5 * (h + 1) * scale);
            back_shield.setTranslateZ(0.5 * (d + 1) * scale);
        } else {
            back_shield = new ImageView();
            back_shield.setVisible(false);
        }

        if (TOP_SHIELD != null) {
            top_shield = new ImageView(Utils.resizeImg(new WritableImage(skin.getPixelReader(), TOP_SHIELD.x, TOP_SHIELD.y, TOP_SHIELD.w, TOP_SHIELD.h), (TOP_SHIELD.w + 1) * scale, (TOP_SHIELD.h + 1) * scale));
            top_shield.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS));
            top_shield.setTranslateX(-0.5 * (w + 1) * scale);
            top_shield.setTranslateY(-0.5 * (h + 1) * scale);
            top_shield.setTranslateZ(0.5 * (d + 1) * scale);
        } else {
            top_shield = new ImageView();
            top_shield.setVisible(false);
        }

        if (BOTTOM_SHIELD != null) {
            bottom_shield = new ImageView(Utils.resizeImg(new WritableImage(skin.getPixelReader(), BOTTOM_SHIELD.x, BOTTOM_SHIELD.y, BOTTOM_SHIELD.w, BOTTOM_SHIELD.h), (BOTTOM_SHIELD.w + 1) * scale, (BOTTOM_SHIELD.h + 1) * scale));
            bottom_shield.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS));
            bottom_shield.setTranslateX(-0.5 * (w + 1) * scale);
            bottom_shield.setTranslateY(0.5 * (h + 1) * scale);
            bottom_shield.setTranslateZ(-0.5 * (d + 1) * scale);
        } else {
            bottom_shield = new ImageView();
            bottom_shield.setVisible(false);
        }

        if (RIGHT_SHIELD != null) {
            right_shield = new ImageView(Utils.resizeImg(new WritableImage(skin.getPixelReader(), RIGHT_SHIELD.x, RIGHT_SHIELD.y, RIGHT_SHIELD.w, RIGHT_SHIELD.h), (RIGHT_SHIELD.w + 1) * scale, (RIGHT_SHIELD.h + 1) * scale));
            right_shield.getTransforms().setAll(new Rotate(-90, Rotate.Y_AXIS));
            right_shield.setTranslateX(0.5 * (w + 1) * scale);
            right_shield.setTranslateY(-0.5 * (h + 1) * scale);
            right_shield.setTranslateZ(-0.5 * (d + 1) * scale);
        } else {
            right_shield = new ImageView();
            right_shield.setVisible(false);
        }

        if (LEFT_SHIELD != null) {
            left_shield = new ImageView(Utils.resizeImg(new WritableImage(skin.getPixelReader(), LEFT_SHIELD.x, LEFT_SHIELD.y, LEFT_SHIELD.w, LEFT_SHIELD.h), (LEFT_SHIELD.w + 1) * scale, (LEFT_SHIELD.h + 1) * scale));
            left_shield.getTransforms().setAll(new Rotate(90, Rotate.Y_AXIS));
            left_shield.setTranslateX(-0.5 * (w + 1) * scale);
            left_shield.setTranslateY(-0.5 * (h + 1) * scale);
            left_shield.setTranslateZ(0.5 * (d + 1) * scale);
        } else {
            left_shield = new ImageView();
            left_shield.setVisible(false);
        }


        getChildren().addAll(
                front, back, top, bottom, right, left
        );
        //Group surfaces = new Group(front,top,right);

        getTransforms().addListener((ListChangeListener<? super Transform>) c -> {
            AtomicReference<Double> x = new AtomicReference<>((double) 0);
            AtomicReference<Double> y = new AtomicReference<>((double) 0);
            AtomicReference<Double> z = new AtomicReference<>((double) 1);
            c.getList().forEach(transform -> {
                if (transform instanceof Rotate) {
                    Rotate rotate = (Rotate) transform;
                    double angle = rotate.getAngle();
                    angle = (Math.abs(angle) % 360) * Math.signum(angle);
                    angle = Math.abs(angle) <= 180 ? angle : (360 - Math.abs(angle)) * Math.signum(angle) * (-1);
                    if (rotate.getAxis().equals(Rotate.X_AXIS)) {
                        double[] tmp = Utils.rotate(new double[]{z.get(), y.get()}, angle);
                        z.set(tmp[0]);
                        y.set(tmp[1]);
                    } else if (rotate.getAxis().equals(Rotate.Y_AXIS)) {
                        double[] tmp = Utils.rotate(new double[]{x.get(), z.get()}, angle);
                        x.set(tmp[0]);
                        z.set(tmp[1]);
                    }
                }
            });
            if (x.get() < 0) {
                leftVisible = false;
                rightVisible = true;
            } else if (x.get() > 0) {
                leftVisible = true;
                rightVisible = false;
            } else {
                leftVisible = false;
                rightVisible = false;
            }
            if (y.get() < 0) {
                topVisible = false;
                bottomVisible = true;
            } else if (y.get() > 0) {
                topVisible = true;
                bottomVisible = false;
            } else {
                topVisible = false;
                bottomVisible = false;
            }
            if (z.get() < 0) {
                frontVisible = false;
                backVisible = true;
            } else if (z.get() > 0) {
                frontVisible = true;
                backVisible = false;
            } else {
                frontVisible = false;
                backVisible = false;
            }
            surfacesA.clear();
            surfacesB.clear();
            surfacesC.clear();
            if (frontVisible) {
                surfacesA.add(back_shield);
                surfacesB.add(front);
                surfacesC.add(front_shield);
            } else {
                surfacesA.add(front_shield);
                surfacesB.add(back);
                surfacesC.add(back_shield);
            }
            if (leftVisible) {
                surfacesA.add(right_shield);
                surfacesB.add(left);
                surfacesC.add(left_shield);
            } else {
                surfacesA.add(left_shield);
                surfacesB.add(right);
                surfacesC.add(right_shield);
            }
            if (topVisible) {
                surfacesA.add(bottom_shield);
                surfacesB.add(top);
                surfacesC.add(top_shield);
            } else {
                surfacesA.add(top_shield);
                surfacesB.add(bottom);
                surfacesC.add(bottom_shield);
            }
            Platform.runLater(() -> {
                getChildren().clear();
                getChildren().addAll(surfacesA);
                getChildren().addAll(surfacesB);
                getChildren().addAll(surfacesC);
            });
        });

    }

    public void calcVisibleSides(Rotate... rotates) {
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);
        AtomicReference<Double> z = new AtomicReference<>((double) 1);
        Arrays.asList(rotates).forEach(rotate -> {
            double angle = rotate.getAngle();
            angle = (Math.abs(angle) % 360) * Math.signum(angle);
            angle = Math.abs(angle) <= 180 ? angle : (360 - Math.abs(angle)) * Math.signum(angle) * (-1);
            if (rotate.getAxis().equals(Rotate.X_AXIS)) {
                double[] tmp = Utils.rotate(new double[]{z.get(), y.get()}, angle);
                z.set(tmp[0]);
                y.set(tmp[1]);
            } else if (rotate.getAxis().equals(Rotate.Y_AXIS)) {
                double[] tmp = Utils.rotate(new double[]{x.get(), z.get()}, angle);
                x.set(tmp[0]);
                z.set(tmp[1]);
            }
        });
        if (x.get() < 0) {
            leftVisible = false;
            rightVisible = true;
        } else if (x.get() > 0) {
            leftVisible = true;
            rightVisible = false;
        } else {
            leftVisible = false;
            rightVisible = false;
        }
        if (y.get() < 0) {
            topVisible = false;
            bottomVisible = true;
        } else if (y.get() > 0) {
            topVisible = true;
            bottomVisible = false;
        } else {
            topVisible = false;
            bottomVisible = false;
        }
        if (z.get() < 0) {
            frontVisible = false;
            backVisible = true;
        } else if (z.get() > 0) {
            frontVisible = true;
            backVisible = false;
        } else {
            frontVisible = false;
            backVisible = false;
        }
        surfacesA.clear();
        surfacesB.clear();
        surfacesC.clear();
        if (frontVisible) {
            surfacesA.add(back_shield);
            surfacesB.add(front);
            surfacesC.add(front_shield);
        } else {
            surfacesA.add(front_shield);
            surfacesB.add(back);
            surfacesC.add(back_shield);
        }
        if (leftVisible) {
            surfacesA.add(right_shield);
            surfacesB.add(left);
            surfacesC.add(left_shield);
        } else {
            surfacesA.add(left_shield);
            surfacesB.add(right);
            surfacesC.add(right_shield);
        }
        if (topVisible) {
            surfacesA.add(bottom_shield);
            surfacesB.add(top);
            surfacesC.add(top_shield);
        } else {
            surfacesA.add(top_shield);
            surfacesB.add(bottom);
            surfacesC.add(bottom_shield);
        }
        Platform.runLater(() -> {
            getChildren().clear();
            getChildren().addAll(surfacesA);
            getChildren().addAll(surfacesB);
            getChildren().addAll(surfacesC);
        });
    }

    public void setLocate(double x, double y, double z) {
        locate = new Point3D(x, y, z);
    }

    @Data
    public static class Piece {

        int x, y;
        int w, h;

        public Piece(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public static Piece of(int x, int y, int w, int h) {
            return new Piece(x, y, w, h);
        }

        public static Piece[] gen(int x,int y,int w,int h,int d) {
            return new Piece[]{
                    Piece.of(x+d,y,w,d),
                    Piece.of(x+d+w,y,w,d),
                    Piece.of(x,y+d,d,h),
                    Piece.of(x+d+w,y+d,d,h),
                    Piece.of(x+d,y+d,w,h),
                    Piece.of(x+d+w+d,y+d,w,h),
            };
        }

    }

}
