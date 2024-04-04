package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FavoriteView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import models.Favorite;
import services.FavoriteService;

/**
 * 「いいね」に関する処理を行うActionクラス
 *
 */
public class FavoriteAction extends ActionBase {

    private FavoriteService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FavoriteService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 「いいね」登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //idを条件に日報データを取得
            ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));


            //パラメータの値を元にいいね情報のインスタンスを作成する
            FavoriteView fv = new FavoriteView(
                    null,
                    ev, //ログインしている従業員を、いいねを押した人として登録する
                    rv, //いいねを押された日報
                    null,
                    null);

            //いいね情報登録
            service.create(fv);

            //一覧画面へリダイレクト
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * いいねの取り消しを行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //idを条件に日報データを取得
            ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //従業員IDと日報IDをもとにいいねデータを取得
            Favorite f = service.findOne(ev, rv);

            //いいねデータを削除する
            service.destroy(f);

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
        }
    }

}







