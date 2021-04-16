package com.liziyi0914.lnmcl.msv;

import javafx.geometry.Point3D;

public class Model {

    public Block.Piece[] HEAD = new Block.Piece[12],
            BODY= new Block.Piece[12],
            HAND_L= new Block.Piece[12],
            HAND_R= new Block.Piece[12],
            LEG_L= new Block.Piece[12],
            LEG_R= new Block.Piece[12];
    public Point3D HEAD_LOCATION = Point3D.ZERO,
            BODY_LOCATION = Point3D.ZERO,
            HAND_L_LOCATION = Point3D.ZERO,
            HAND_R_LOCATION = Point3D.ZERO,
            LEG_L_LOCATION = Point3D.ZERO,
            LEG_R_LOCATION = Point3D.ZERO;

    public Model head(Block.Piece[] pieces, Block.Piece[] pieces_shield) {
        System.arraycopy(pieces,0,this.HEAD,0,6);
        System.arraycopy(pieces_shield,0,this.HEAD,6,6);
        return this;
    }

    public Model body(Block.Piece[] pieces, Block.Piece[] pieces_shield) {
        System.arraycopy(pieces,0,this.BODY,0,6);
        System.arraycopy(pieces_shield,0,this.BODY,6,6);
        return this;
    }

    public Model leftHand(Block.Piece[] pieces, Block.Piece[] pieces_shield) {
        System.arraycopy(pieces,0,this.HAND_L,0,6);
        System.arraycopy(pieces_shield,0,this.HAND_L,6,6);
        return this;
    }

    public Model rightHand(Block.Piece[] pieces, Block.Piece[] pieces_shield) {
        System.arraycopy(pieces,0,this.HAND_R,0,6);
        System.arraycopy(pieces_shield,0,this.HAND_R,6,6);
        return this;
    }

    public Model leftLeg(Block.Piece[] pieces, Block.Piece[] pieces_shield) {
        System.arraycopy(pieces,0,this.LEG_L,0,6);
        System.arraycopy(pieces_shield,0,this.LEG_L,6,6);
        return this;
    }

    public Model rightLeg(Block.Piece[] pieces, Block.Piece[] pieces_shield) {
        System.arraycopy(pieces,0,this.LEG_R,0,6);
        System.arraycopy(pieces_shield,0,this.LEG_R,6,6);
        return this;
    }

    public Model headAt(double x,double y,double z) {
        this.HEAD_LOCATION = new Point3D(x,y,z);
        return this;
    }

    public Model bodyAt(double x,double y,double z) {
        this.BODY_LOCATION = new Point3D(x,y,z);
        return this;
    }

    public Model leftHandAt(double x,double y,double z) {
        this.HAND_L_LOCATION = new Point3D(x,y,z);
        return this;
    }

    public Model rightHandAt(double x,double y,double z) {
        this.HAND_R_LOCATION = new Point3D(x,y,z);
        return this;
    }

    public Model leftLegAt(double x,double y,double z) {
        this.LEG_L_LOCATION = new Point3D(x,y,z);
        return this;
    }

    public Model rightLegAt(double x,double y,double z) {
        this.LEG_R_LOCATION = new Point3D(x,y,z);
        return this;
    }

    public static final Model STEVE = new Model()
            .head(Block.Piece.gen(0,0,8,8,8), Block.Piece.gen(32,0,8,8,8))
            .headAt(0,-80,0)
            .body(Block.Piece.gen(16,16,8,12,4), Block.Piece.gen(16,32,8,12,4))
            .leftHand(Block.Piece.gen(32,48,4,12,4), Block.Piece.gen(48,48,4,12,4))
            .leftHandAt(48,0,0)
            .rightHand(Block.Piece.gen(40,16,4,12,4), Block.Piece.gen(40,32,4,12,4))
            .rightHandAt(-48,0,0)
            .leftLeg(Block.Piece.gen(16,48,4,12,4), Block.Piece.gen(0,48,4,12,4))
            .leftLegAt(16,96,0)
            .rightLeg(Block.Piece.gen(0,16,4,12,4), Block.Piece.gen(0,32,4,12,4))
            .rightLegAt(-16,96,0);

    public static final Model ALEX = new Model()
            .head(Block.Piece.gen(0,0,8,8,8), Block.Piece.gen(32,0,8,8,8))
            .headAt(0,-80,0)
            .body(Block.Piece.gen(16,16,8,12,4), Block.Piece.gen(16,32,8,12,4))
            .leftHand(Block.Piece.gen(32,48,3,12,4), Block.Piece.gen(48,48,3,12,4))
            .leftHandAt(40,0,0)
            .rightHand(Block.Piece.gen(40,16,3,12,4), Block.Piece.gen(40,32,3,12,4))
            .rightHandAt(-40,0,0)
            .leftLeg(Block.Piece.gen(16,48,4,12,4), Block.Piece.gen(0,48,4,12,4))
            .leftLegAt(16,96,0)
            .rightLeg(Block.Piece.gen(0,16,4,12,4), Block.Piece.gen(0,32,4,12,4))
            .rightLegAt(-16,96,0);

    /*public static final Model ALEX = new Model()
            .head(Block.Piece.of(8, 0, 8, 8),
                    Block.Piece.of(16, 0, 8, 8),
                    Block.Piece.of(0, 8, 8, 8),
                    Block.Piece.of(16, 8, 8, 8),
                    Block.Piece.of(8, 8, 8, 8),
                    Block.Piece.of(24, 8, 8, 8),
                    Block.Piece.of(40, 0, 8, 8),
                    Block.Piece.of(48, 0, 8, 8),
                    Block.Piece.of(32, 8, 8, 8),
                    Block.Piece.of(48, 8, 8, 8),
                    Block.Piece.of(40, 8, 8, 8),
                    Block.Piece.of(56, 8, 8, 8))
            .headAt(0,-80,0)
            .body(Block.Piece.of(20, 16, 8, 4),
                    Block.Piece.of(28, 16, 8, 4),
                    Block.Piece.of(16, 20, 4, 12),
                    Block.Piece.of(28, 20, 4, 12),
                    Block.Piece.of(20, 20, 8, 12),
                    Block.Piece.of(32, 20, 8, 12),
                    Block.Piece.of(20, 32, 8, 4),
                    Block.Piece.of(28, 32, 8, 4),
                    Block.Piece.of(16, 36, 4, 12),
                    Block.Piece.of(28, 36, 4, 12),
                    Block.Piece.of(20, 36, 8, 12),
                    Block.Piece.of(32, 36, 8, 12))
            .leftHand(Block.Piece.of(36, 48, 3, 4),
                    Block.Piece.of(39, 48, 3, 4),
                    Block.Piece.of(32, 52, 4, 12),
                    Block.Piece.of(36, 52, 4, 12),
                    Block.Piece.of(40, 52, 4, 12),
                    Block.Piece.of(44, 52, 4, 12),
                    Block.Piece.of(52, 48, 4, 4),
                    Block.Piece.of(56, 48, 4, 4),
                    Block.Piece.of(48, 52, 4, 12),
                    Block.Piece.of(52, 52, 4, 12),
                    Block.Piece.of(56, 52, 4, 12),
                    Block.Piece.of(60, 52, 4, 12))
            .leftHandAt(48,0,0)
            .rightHand(Block.Piece.of(44, 16, 4, 4),
                    Block.Piece.of(48, 16, 4, 4),
                    Block.Piece.of(40, 20, 4, 12),
                    Block.Piece.of(48, 20, 4, 12),
                    Block.Piece.of(44, 20, 4, 12),
                    Block.Piece.of(52, 20, 4, 12),
                    Block.Piece.of(44, 32, 4, 4),
                    Block.Piece.of(48, 32, 4, 4),
                    Block.Piece.of(40, 36, 4, 12),
                    Block.Piece.of(48, 36, 4, 12),
                    Block.Piece.of(44, 36, 4, 12),
                    Block.Piece.of(52, 36, 4, 12))
            .rightHandAt(-48,0,0)
            .leftLeg(Block.Piece.of(20, 48, 4, 4),
                    Block.Piece.of(24, 48, 4, 4),
                    Block.Piece.of(16, 52, 4, 12),
                    Block.Piece.of(24, 52, 4, 12),
                    Block.Piece.of(20, 52, 4, 12),
                    Block.Piece.of(28, 52, 4, 12),
                    Block.Piece.of(4, 48, 4, 4),
                    Block.Piece.of(8, 48, 4, 4),
                    Block.Piece.of(0, 52, 4, 12),
                    Block.Piece.of(8, 52, 4, 12),
                    Block.Piece.of(4, 52, 4, 12),
                    Block.Piece.of(12, 52, 4, 12))
            .leftLegAt(16,96,0)
            .rightLeg(Block.Piece.of(4, 16, 4, 4),
                    Block.Piece.of(8, 16, 4, 4),
                    Block.Piece.of(0, 20, 4, 12),
                    Block.Piece.of(8, 20, 4, 12),
                    Block.Piece.of(4, 20, 4, 12),
                    Block.Piece.of(12, 20, 4, 12),
                    Block.Piece.of(4, 32, 4, 4),
                    Block.Piece.of(8, 32, 4, 4),
                    Block.Piece.of(0, 36, 4, 12),
                    Block.Piece.of(8, 36, 4, 12),
                    Block.Piece.of(4, 36, 4, 12),
                    Block.Piece.of(12, 36, 4, 12))
            .rightLegAt(-16,96,0);*/

}
