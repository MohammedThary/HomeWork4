/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw4;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author wwwmo
 */
public class FirstController implements Initializable {

    @FXML
    private BorderPane main_pane;
    @FXML
    private TextField id_tf;
    @FXML
    private TextField name_tf;
    @FXML
    private TextField grade_tf;
    @FXML
    private TextField major_tf;
    @FXML
    private Button show_btn;
    @FXML
    private Button add_btn;
    @FXML
    private Button update_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TextArea text_area;
    @FXML
    private TableView<Student> student_table;
    @FXML
    private TableColumn<Student, Integer> student_id_col;
    @FXML
    private TableColumn<Student, String> student_name_col;
    @FXML
    private TableColumn<Student, Integer> student_grade_col;
    @FXML
    private TableColumn<Student, String> student_major_col;
    @FXML
    private TableView<Registration> registration_table;
    @FXML
    private TableColumn<Registration, Integer> registration_sid_col;
    @FXML
    private TableColumn<Registration, Integer> registration_cid_col;
    @FXML
    private TableColumn<Registration, String> registration_semester_col;
    @FXML
    private Button SE_S_btn;
    @FXML
    private Button best_s_btn;
    @FXML
    private Button pass_btn;
    @FXML
    private Button less_grade;
    Statement st;
    @FXML
    private TextField Cid_tf;
    @FXML
    private TextField Sid_tf;
    @FXML
    private TextField semester_tf;
    @FXML
    private TableView<course> course_table;
    @FXML
    private TableColumn<course, Integer> course_id_col;
    @FXML
    private TableColumn<course, Integer> course_name_col;
    @FXML
    private TableColumn<course, String> course_room_col;
     private ResultSet rs;
    @FXML
    private Button add_to_Registration_btn;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.
                    getConnection("jdbc:mysql://127.0.0.1:3306/jhw4?serverTimezone=UTC","root","");
              this.st = connection.createStatement();
              this.show_course();
               this.show_reg();
        } catch (Exception ex) {
            ex.printStackTrace() ;
        }
        student_table.getSelectionModel().selectedItemProperty().addListener(
         event-> show_selected_student()
        );
        
        
        
           
            
            
            
            
        
        
    }
        
   
    @FXML
    private void btn_show_handel(ActionEvent event) throws Exception {
        this.clear();
        this.show();
    }
   
    @FXML
    private void btn_add_handel(ActionEvent event) throws SQLException {
        Integer id = Integer.parseInt(id_tf.getText());
        Integer grade = Integer.parseInt(grade_tf.getText());
        String name = name_tf.getText();
        String major = major_tf.getText();
        String sql = "insert into student values ("+id+",'"+name+"','"+major+"','"+grade+"')";
        this.st.executeUpdate(sql);
       this.clear();
       this.show();
        
    }

    @FXML
    private void btn_update_handel(ActionEvent event) throws SQLException {
        Integer id = Integer.parseInt(id_tf.getText());
        Integer grade = Integer.parseInt(grade_tf.getText());
        String name = name_tf.getText();
        String major = major_tf.getText();
        String sql = "update student set name = '"+name+"',major = '"+major+"', grade ='"+grade+"' where id = "+id;
        this.st.executeUpdate(sql);
        this.clear();
        this.show();
    }

    @FXML
    private void btn_delete_handel(ActionEvent event) throws SQLException {     
         String sql = "delete from student where  id = '"+Integer.parseInt(id_tf.getText())+"'";
         this.st.executeUpdate(sql);
         this.clear();
         this.show();    
    }

    @FXML
    private void btn_SE_S_handel(ActionEvent event) throws SQLException {
        System.out.println("done");
        ResultSet rs = this.st.executeQuery("Select * From student where major ='SE'");
        student_table.getItems().clear();
        while (rs.next()) {
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setGrade(rs.getInt("grade"));
            stu.setMajor(rs.getString("major"));
            stu.setName(rs.getString("name"));
            student_table.getItems().add(stu);
        }
        
    }

    @FXML
    private void btn_best_S_handel(ActionEvent event) throws SQLException {
        ResultSet rs = this.st.executeQuery("Select * From student where grade >=90");
        student_table.getItems().clear();
        while (rs.next()) {
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setGrade(rs.getInt("grade"));
            stu.setMajor(rs.getString("major"));
            stu.setName(rs.getString("name"));
            student_table.getItems().add(stu);
        }
    }

    @FXML
    private void btn_pass_S_handel(ActionEvent event) throws SQLException {
        ResultSet rs = this.st.executeQuery("Select * From student where grade >=60");
        student_table.getItems().clear();
        while (rs.next()) {
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setGrade(rs.getInt("grade"));
            stu.setMajor(rs.getString("major"));
            stu.setName(rs.getString("name"));
            student_table.getItems().add(stu);
        }
    }

    @FXML
    private void btn_less_S_handel(ActionEvent event) throws SQLException {
        String sql = "update student set grade = (grade+3) where  (major = 'CS' and grade<70)";
        this.st.executeUpdate(sql);
        this.clear();
        this.show();
        
    }
    
    
    
    
    
    
     private void show_selected_student (){
        Student stu = student_table.getSelectionModel().getSelectedItem();
         if (stu !=null) {
           id_tf.setText(String.valueOf(stu.getId()));
        name_tf.setText(stu.getName());
        major_tf.setText(stu.getMajor());
       grade_tf.setText(String.valueOf(stu.getGrade()));
       student_table.getItems().clear();  
         }
        
    }
     private void clear (){
        id_tf.setText("");
        name_tf.setText("");
        major_tf.setText("");
       grade_tf.setText("");
       student_table.getItems().clear();
    } 
    
     private void show() throws SQLException{
   System.out.println("done");
        ResultSet rs = this.st.executeQuery("Select * From student");
        student_table.getItems().clear();
        while (rs.next()) {
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setGrade(rs.getInt("grade"));
            stu.setMajor(rs.getString("major"));
            stu.setName(rs.getString("name"));
            student_table.getItems().add(stu);
        }
   }
      private void clear_reg(){
        Sid_tf.setText("");
        Cid_tf.setText("");
        semester_tf.setText("");
       registration_table.getItems().clear();
    }
     private void show_reg() throws SQLException{
        ResultSet rs = this.st.executeQuery("Select * From registration");
        registration_table.getItems().clear();
        while (rs.next()) {
            Registration reg = new Registration();
            reg.setStudent_id(rs.getInt("student_id"));
            reg.setCourse_id(rs.getInt("course_id"));
            reg.setSemester(rs.getString("semester"));          
            registration_table.getItems().add(reg);
        }
   }
     
     private void show_course() throws Exception{
try {
            student_id_col.setCellValueFactory(new PropertyValueFactory("id"));
            student_name_col.setCellValueFactory(new PropertyValueFactory("name"));
            student_major_col.setCellValueFactory(new PropertyValueFactory("major"));
            student_grade_col.setCellValueFactory(new PropertyValueFactory("grade"));   
            
            registration_sid_col.setCellValueFactory(new PropertyValueFactory("student_id"));
            registration_cid_col.setCellValueFactory(new PropertyValueFactory("course_id"));
            registration_semester_col.setCellValueFactory(new PropertyValueFactory("semseter")); 
            
            course_id_col.setCellValueFactory(new PropertyValueFactory("id"));
            course_name_col.setCellValueFactory(new PropertyValueFactory("name"));
            course_room_col.setCellValueFactory(new PropertyValueFactory("room")); 
            rs = this.st.executeQuery("Select * From course");
            course_table.getItems().clear();
        while (rs.next()) {
            course cou = new course();
            cou.setId(rs.getInt("id"));
            cou.setName(rs.getString("name"));
            cou.setRoom(rs.getString("room"));
            
            course_table.getItems().add(cou);
        }
        } catch (SQLException ex) {
            Logger.getLogger(FirstController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    @FXML
    private void btn_add_to_Registration_handel(ActionEvent event) throws SQLException {
       
        Integer Student_id = Integer.parseInt(Sid_tf.getText());
        Integer Course_id = Integer.parseInt(Cid_tf.getText());
        String semester = semester_tf.getText();     
        String sql = "insert into registration values ("+Student_id+",'"+Course_id+"','"+semester+"')";
        this.st.executeUpdate(sql);
        this.clear_reg();
         this.show_reg();
    }

   

}
