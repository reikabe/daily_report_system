package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.WorkmanagementConverter;
import actions.views.WorkmanagementView;
import constants.JpaConst;
import models.Workmanagement;

/**
 * 勤務管理のテーブル操作にかかわる処理を行う
 *
 */
public class WorkmanagementService extends ServiceBase{

    /**
     * 指定した従業員の勤務データの件数を取得し、返却する
     * @param employee
     * @return 勤務データの件数
     */

    public long countAllMine(EmployeeView employee) {

        long count = (long)em.createNamedQuery(JpaConst.Q_WOR_COUNT_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定した従業員の勤務データを取得し、WorkmanagementViewのリストで返却する
     * @return 一覧画面に表示するデータのリスト
     */
    public List<WorkmanagementView> getAllMine(EmployeeView employee,LocalDate yearmonth)
{
        List<Workmanagement> workmanagements = em.createNamedQuery(JpaConst.Q_WOR_GET_ALL_MINE, Workmanagement.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setParameter(JpaConst.JPQL_PARM_YEARMONTH, yearmonth)
                .getResultList();
        return WorkmanagementConverter.toViewList(workmanagements);
    }

    /**
     * 出勤の日付が登録されているか検索し、workmanagementviewのリストで返却
     */

    public List<WorkmanagementView> findentry(EmployeeView ev ){
        List<Workmanagement> workmanagements = em.createNamedQuery(JpaConst.Q_WOR_GET_EMPLOYEEDATE, Workmanagement.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getResultList();
        return WorkmanagementConverter.toViewList(workmanagements);

    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Workmanagement findOneInternal(int id) {
        return em.find(Workmanagement.class, id);
    }

    /**
     * 指定した従業員の退勤の日付を登録する
     * flag = 0 出勤メソッドから
     * flag = 1 退勤メソッドから
     */
    public void create(WorkmanagementView wv,int flag) {

        em.getTransaction().begin();
        if(flag == 0) {
            if(wv.getGo_Time() == null && wv.getBack_Time() == null){
                wv.setGo_Time(LocalDateTime.now());
                wv.setWorkmanagementDate(LocalDate.now());
                em.persist(WorkmanagementConverter.toModel(wv));
                em.getTransaction().commit();
            }

        }else {
                wv.setBack_Time(LocalDateTime.now());
                Workmanagement w = findOneInternal(wv.getId());
                WorkmanagementConverter.copyViewToModel(w, wv);
                em.getTransaction().commit();

        }

    }

}
