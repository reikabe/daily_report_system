package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 勤務情報について画面の入力値・出力値を扱うViewモデル
 *
 */

@Getter //すべてのクラスフィールドについてgetterを自動生成(Lombok)
@Setter //Lombok
@NoArgsConstructor //Lombok
@AllArgsConstructor //Lombok

public class WorkmanagementView {

    /**
     * id
     */

    private Integer id;

    /**
     * 勤務情報を登録した従業員
     */

    private EmployeeView employee;

    /**
     * いつの勤務情報かを示す日付
     */

    private LocalDate workmanagementDate;

    /**
     * 出勤日時
     */

    private LocalDateTime go_Time;

    /**
     * 退勤日時
     */

    private LocalDateTime back_Time;


}
