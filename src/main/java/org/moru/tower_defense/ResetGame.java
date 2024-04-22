package org.moru.tower_defense;
/*
このクラスは、Tower_Defenseプラグインのリセットゲームクラスです。
ゲームをリセットするためのメソッドがあります。
Wolrdeditを使用して、ゲーム内のエンティティを削除します。
config.ymlに
Delete_Section:
    pos1: <x> <y> <z>
    pos2: <x> <y> <z>
をかく。
CommandTDクラスのにて
/td clearpos1 <x> <y> <z> → pos1の座標を設定(config.ymlに書き込む)
/td clearpos2 <x> <y> <z> → pos2の座標を設定
/td clear → pos1とpos2の範囲内のエンティティを削除
 */



public class ResetGame {
    public void setPos(int position,int x, int y, int z) {
        //config.ymlに書き込む

    }
}
