package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 勤務データのDTOモデル
 */
@Table(name = JpaConst.TABLE_WOR)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_WOR_COUNT,
            query = JpaConst.Q_WOR_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_WOR_COUNT_MINE,
            query = JpaConst.Q_WOR_COUNT_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_WOR_GET_ALL,
            query = JpaConst.Q_WOR_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_WOR_GET_ALL_MINE,
            query = JpaConst.Q_WOR_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_WOR_GET_EMPLOYEEDATE,
            query = JpaConst.Q_WOR_GET_EMPLOYEEDATE_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Workmanagement {

    /**
     * id
     */

    @Id
    @Column(name = JpaConst.WOR_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 勤務管理を登録した従業員
     */

    @ManyToOne
    @JoinColumn(name = JpaConst.WOR_COL_EMP,nullable = false)
    private Employee employee;

    /**
     * 出勤日
     */
    @Column(name = JpaConst.WOR_COL_DATE,nullable = false)
    private LocalDate workmanagementDate;

    /**
     * 出勤時間の日時
     *
     */

    @Column(name = JpaConst.WOR_COL_GO,nullable = true)
    private LocalDateTime goTime;

    /**
     * 退勤時間の日時
     */

    @Column(name = JpaConst.WOR_COL_BACK,nullable = true)
    private LocalDateTime backTime;



}
