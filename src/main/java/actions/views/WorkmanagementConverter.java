package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Workmanagement;

/**
 *勤務管理データのDTOモデル⇔Viewモデルの返還を行うクラス
 *
 */

public class WorkmanagementConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param wv WorkmanagementViewのインスタンス
     * @return Workmanagementのインスタンス
     */

    public static Workmanagement toModel(WorkmanagementView wv) {

        return new Workmanagement(
                wv.getId(),
                EmployeeConverter.toModel(wv.getEmployee()),
                wv.getWorkmanagementDate(),
                wv.getGo_Time(),
                wv.getBack_Time());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param w Workmanagementのインスタンス
     * @return WorkmanagementViewのインスタンス
     */

    public static WorkmanagementView toView(Workmanagement w) {

        if(w == null) {
            return null;
        }
        return new WorkmanagementView(
                w.getId(),
                EmployeeConverter.toView(w.getEmployee()),
                w.getWorkmanagementDate(),
                w.getGoTime(),
                w.getBackTime());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */

    public static List<WorkmanagementView> toViewList(List<Workmanagement>list){
        List<WorkmanagementView> evs = new ArrayList<>();

        for(Workmanagement w : list) {
            evs.add(toView(w));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param w DTOモデル(コピー先)
     * @param wv Viewモデル(コピー元)
     */

    public static void copyViewToModel(Workmanagement w,WorkmanagementView wv) {
        w.setId(wv.getId());
        w.setEmployee(EmployeeConverter.toModel(wv.getEmployee()));
        w.setWorkmanagementDate(wv.getWorkmanagementDate());
        w.setGoTime(wv.getGo_Time());
        w.setBackTime(wv.getBack_Time());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */

    public static WorkmanagementView toListView(List<WorkmanagementView> list){
        WorkmanagementView wv = new WorkmanagementView();

        for(WorkmanagementView w : list) {
            wv = w;
        }

        return wv;
    }


}
