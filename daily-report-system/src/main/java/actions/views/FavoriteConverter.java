package actions.views;

import models.Favorite;

/**
 * 「いいね」のDTOモデル↔Viewモデルの変換を行うクラス
 *
 */
public class FavoriteConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param fv FavoriteViewのインスタンス
     * @return Favoriteのインスタンス
     */
    public static Favorite toModel(FavoriteView fv) {
        return new Favorite(
                fv.getId(),
                EmployeeConverter.toModel(fv.getEmployee()),
                ReportConverter.toModel(fv.getReport()),
                fv.getCreatedAt(),
                fv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param f Favoriteのインスタンス
     * @return FavoriteViewのインスタンス
     */
    public static FavoriteView toView(Favorite f) {

        if (f == null) {
            return null;
        }

        return new FavoriteView(
                f.getId(),
                EmployeeConverter.toView(f.getEmployee()),
                ReportConverter.toView(f.getReport()),
                f.getCreatedAt(),
                f.getUpdatedAt());
    }
}