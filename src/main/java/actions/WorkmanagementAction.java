package actions;

    import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.WorkmanagementConverter;
import actions.views.WorkmanagementView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.WorkmanagementService;

    /**
     * 勤務管理に関する処理を行うクラス
     *
     */
    public class WorkmanagementAction extends ActionBase{
        private WorkmanagementService service;

        /**
         * メソッド実行
         *
         */

        @Override
        public void process() throws ServletException,IOException{
            service = new WorkmanagementService();

            //メソッド実行
            invoke();

            service.close();
        }

        /**
         * 勤務履歴を表示
         * @throws ServlsetException
         * @throws IOException
         */

        public void index() throws ServletException,IOException{

            List<LocalDate> day = new LinkedList<>();
            List<String> gotime = new LinkedList<>();
            List<String> backtime = new LinkedList<>();

            //リクエストスコープに値が入っていないときのみ現在の日付を取得

            LocalDate now = LocalDate.now();

            LocalDate firstdate = now.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastdate = now.with(TemporalAdjusters.lastDayOfMonth());

            if(getRequestParam(AttributeConst.WOR_MONTH) != null){

                String month = getRequestParam(AttributeConst.WOR_MONTH);

                if(Integer.parseInt(getRequestParam(AttributeConst.WOR_MONTH)) -10 < 0) {
                    month = "0"+getRequestParam(AttributeConst.WOR_MONTH);
                }
                String date = getRequestParam(AttributeConst.WOR_YEAR)+"-"+month+"-01";
                firstdate = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                lastdate = firstdate.with(TemporalAdjusters.lastDayOfMonth());
                now = firstdate;
            }

            //月の勤務履歴のデータを取得
            EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
            List<WorkmanagementView> workdate = service.getAllMine(loginEmployee,firstdate);

            //月の日付の数だけ取得
            for(int i = firstdate.getDayOfMonth();i <= lastdate.getDayOfMonth();i++) {

                for(int indexcount = 0;indexcount < workdate.size();indexcount++) {

                    if(workdate.get(indexcount).getWorkmanagementDate().getDayOfMonth() == firstdate.getDayOfMonth()) {

                        gotime.add(workdate.get(indexcount).getGo_Time().format(DateTimeFormatter.ofPattern("HH:mm")));
                        if(workdate.get(indexcount).getBack_Time() == null) {
                            backtime.add(null);
                        }else {
                            backtime.add(workdate.get(indexcount).getBack_Time().format(DateTimeFormatter.ofPattern("HH:mm")));
                        }
                        break;

                    }else if(indexcount == workdate.size() -1){
                        gotime.add(null);
                        backtime.add(null);
                    }
                }

                day.add(firstdate);
                firstdate= firstdate.plusDays(1);
            }

            //現在の年月日のデータをリクエストスコープに登録
            putRequestScope(AttributeConst.WOR_YEAR,now.getYear());
            putRequestScope(AttributeConst.WOR_MONTH,now.getMonthValue());
            putRequestScope(AttributeConst.WOR_DAY,day);
            putRequestScope(AttributeConst.WOR_GO,gotime);
            putRequestScope(AttributeConst.WOR_BACK,backtime);

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }
            //一覧画面を表示
            forward(ForwardConst.FW_WOR_INDEX);

        }

        /**
         * 新規登録画面を表示する
         * @throws ServletException
         * @throws IOException
         */

        public void entryNew() throws ServletException,IOException{

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

            //新規登録画面を表示
            forward(ForwardConst.FW_WOR_NEW);
        }

        /**
         * 新規登録を行う
         * goとbackでメソッドを分岐 flag = 0 or 1で管理
         * @throws ServletException
         * @throws IOException
         */

        public void create_go() throws ServletException,IOException{

                //CSRF対策 tokenのチェック
                if (checkToken()) {

                //セッションからログイン中の従業員情報を取得
                EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

                //従業員を入れたほぼ空の勤務情報のインスタンスを作成する
                WorkmanagementView wv = new WorkmanagementView(
                        null,
                        ev, //ログインしている従業員を、日報作成者として登録する
                        null,
                        null,
                        null);

                //指定した従業員が出退勤の登録をしていたか検索
                //検索したid等のデータを元にインスタンスの作成をする
                int flag = 0; //出勤フラグ
                WorkmanagementView w = WorkmanagementConverter.toListView(service.findentry(ev));
                if(w.getId() == null) {
                    service.create(wv, flag);

                    //セッションに登録完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_GO.getMessage());
                }else {
                //既に登録されている場合
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_GO_EXIST.getMessage());
                }
                //entryNewにリダイレクト
                redirect(ForwardConst.ACT_WORK, ForwardConst.CMD_NEW);

                }
        }

        public void create_back() throws ServletException,IOException{


                //CSRF対策 tokenのチェック
                if (checkToken()) {

                  //セッションからログイン中の従業員情報を取得
                  EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);


                  //指定した従業員が出退勤の登録をしていたか検索
                  //検索したid等のデータを元にインスタンスの作成をする
                  int flag = 1; //  退勤フラグ
                  WorkmanagementView wv = WorkmanagementConverter.toListView(service.findentry(ev));
                  if(wv.getId() != null && wv.getBack_Time() == null) {

                      service.create(wv, flag);
                      putSessionScope(AttributeConst.FLUSH, MessageConst.I_BACK.getMessage());

                  }else if(wv.getBack_Time() != null){
                      putSessionScope(AttributeConst.FLUSH, MessageConst.I_BACK_EXIST.getMessage());
                  }else {
                      putSessionScope(AttributeConst.FLUSH, MessageConst.I_NOT_GO.getMessage());
                  }

                  //entryNewにリダイレクト
                  redirect(ForwardConst.ACT_WORK, ForwardConst.CMD_NEW);
                }

          }

        /**
         * indexに表示している出退勤日を編集
         * @throws ServletException
         * @throws IOException
         */

        public void edit() throws ServletException, IOException {

        }

        /**
         * indexに表示している出退勤日を上書き
         * @throws ServletException
         * @throws IOException
         */

        public void update() throws ServletException, IOException {

        }

}
