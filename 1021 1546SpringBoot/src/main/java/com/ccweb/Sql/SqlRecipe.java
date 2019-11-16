package com.ccweb.Sql;

import com.ccweb.Entity.Image;
import com.ccweb.Entity.NutritionInformation;
import com.ccweb.Entity.Recipe;
import com.ccweb.Entity.Steps;

import javax.sound.midi.MidiMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SqlRecipe {
    public Connection getConnection(){
        String driver="com.mysql.cj.jdbc.Driver";   //获取mysql数据库的驱动类
        String url="jdbc:mysql://localhost:3306/ccweb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //连接数据库（kucun是数据库名）
        String name="root";//连接mysql的用户名
        String pwd="LZN951219a";//连接mysql的密码
        try{
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(url,name,pwd);//获取连接对象
            return conn;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Steps> getSteps(String recipieId, Statement stmt){
        List<Steps> steps = new ArrayList<Steps>();

        try{
            String query = "SELECT position, items from Steps where recipieId = " +"\""+recipieId +"\"";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){

                int ps = rs.getInt("position");
                String items = rs.getString("items");
                Steps s = new Steps(ps,items);
                steps.add(s);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }


        return steps;
    }

    //Need query
    public NutritionInformation getNutritionInfo(String recipieId, Statement stmt){
        List<NutritionInformation> nutris = new ArrayList<NutritionInformation>();
        try{
            String query ="select * from NutritionInformation where recipieId='"+recipieId+"'";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){

            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return nutris.size() == 0 ? null: nutris.get(0);
    }

    //Need query
    public Image getImage(String recipieId, Statement stmt){
        List<Image> images = new ArrayList<Image>();
        try{
            String query = "select * from Image where recipieId='\"+recipieId+\"'";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){

            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return images.size() == 0 ? null: images.get(0);

    }


    public List<Recipe> getRecipe(){
        Connection conn = null;
        Statement stmt = null;
        List<Recipe> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sqlrecipe = "SELECT id, created_ts, updated_ts, author_id, " +
                    "cook_time_in_min, prep_time_in_min, total_time_in_min, title, " +
                    "cusine, servings from Recipie";

            ResultSet rs = stmt.executeQuery(sqlrecipe);
            result = new ArrayList<Recipe>();

            while(rs.next()){
                String id = rs.getString("id");
                List<Steps> steps = getSteps(id, stmt);
                NutritionInformation nutritionInformation = getNutritionInfo(id,stmt);
                Image img = getImage(id, stmt);
                String created_ts = rs.getString("created_ts");
                String updated_ts = rs.getString("updated_ts");
                String author_id = rs.getString("author_id");
                //Date create_date = rs.getDate("account_created");
                //string createDateString = create_date.toString();

                int cook_time_in_min = rs.getInt("cook_time_in_min");
                int prep_time_in_min = rs.getInt("prep_time_in_min");
                int total_time_in_min = rs.getInt("total_time_in_min");

                String title = rs.getString("title");
                String cusine = rs.getString("cusine");
                int servings = rs.getInt("servings");


                Recipe item =  new Recipe(id, created_ts, updated_ts, author_id, cook_time_in_min, prep_time_in_min, total_time_in_min, title, cusine, servings);
                item.setSteps(steps);
                item.setNutrition_information(nutritionInformation);
                item.setImage(img);
                result.add(item);


            }

            return result;

        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }


        return  result;

    }


    public Recipe getRecipeById(String id){
        Connection conn = null;
        Statement stmt = null;
        List<Recipe> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sqlrecipe = "SELECT id, created_ts, updated_ts, author_id, cook_time_in_min, prep_time_in_min, total_time_in_min, title, cusine, servings from Recipie where id='"+id+"'";
            ResultSet rs = stmt.executeQuery(sqlrecipe);
            result = new ArrayList<Recipe>();

            while(rs.next()){
               // String id = rs.getString("id");
                String created_ts = rs.getString("created_ts");
                String updated_ts = rs.getString("updated_ts");
                String author_id = rs.getString("author_id");
                //Date create_date = rs.getDate("account_created");
                //string createDateString = create_date.toString();

                int cook_time_in_min = rs.getInt("cook_time_in_min");
                int prep_time_in_min = rs.getInt("prep_time_in_min");
                int total_time_in_min = rs.getInt("total_time_in_min");

                String title = rs.getString("title");
                String cusine = rs.getString("cusine");
                int servings = rs.getInt("servings");

                List<Steps> steps = getSteps(id, stmt);
                NutritionInformation nutritionInformation = getNutritionInfo(id,stmt);
                Image img = getImage(id, stmt);

                Recipe item =  new Recipe(id, created_ts, updated_ts, author_id, cook_time_in_min, prep_time_in_min, total_time_in_min, title, cusine, servings);
                item.setSteps(steps);
                item.setNutrition_information(nutritionInformation);
                item.setImage(img);
                result.add(item);

            }
            //return result;

        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }
        return result.get(0);



    }

    public void updateRecipe(Recipe recipe){


      //   String id = recipe.getId();
        // String created_ts = recipe.getCreated_ts();
        // String updated_ts = recipe.getUpdated_ts();
        // String author_id = recipe.getAuthor_id();
         int cook_time_in_min = recipe.getCook_time_in_min();
         int prep_time_in_min = recipe.getPrep_time_in_min();
        // int total_time_in_min = recipe.getTotal_time_in_min();
         String title = recipe.getTitle();
         String cusine = recipe.getCusine();
         int servings = recipe.getServings();

        Calendar calendar = Calendar.getInstance();
        java.util.Date time = calendar.getTime();
        //String user_updated = time.toString();




        Connection conn = null;
        Statement stmt = null;
        List<Recipe> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String query = String.format(
                            "cook_time_in_min = %s, " +
                            "prep_time_in_min = %s, " +
                            "title = %s, "+
                                    "cusine " +
                            "servings = %s where title = %s",
                    cook_time_in_min,
                    prep_time_in_min,
                    title,
                    cusine,
                    servings
            );
            stmt.execute(query);

        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }

    }

    public void addRecipe(Recipe recipe){
        String id = recipe.getId();
        String created_ts = recipe.getCreated_ts();
        String updated_ts = recipe.getUpdated_ts();
        String author_id = recipe.getAuthor_id();
        int cook_time_in_min = recipe.getCook_time_in_min();
        int prep_time_in_min = recipe.getPrep_time_in_min();
        int total_time_in_min = recipe.getTotal_time_in_min();
        String title = recipe.getTitle();
        String cusine = recipe.getCusine();
        int servings = recipe.getServings();
        List<String> ingredients = recipe.getIngredients();
        List<Steps> steps = recipe.getSteps();
        NutritionInformation nio = recipe.getNutrition_information();



        //Calendar calendar = Calendar.getInstance();
        //Date time = calendar.getTime();
        //String user_created = time.toString();
        //Date userCreate = calendar.getTime();
        //userCreate sql;
        Connection conn = null;
        Statement stmt = null;
        List<Recipe> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
//            String sqlrecipe = "insert into Recipie " +
//                    "(id, created_ts, " +
//                    "updated_ts, " +
//                    "author_id, " +
//                    "cook_time_in_min, " +
//                    "prep_time_in_min, " +
//                    "total_time_in_min, " +
//                    "title, " +
//                    "cusine, " +
//                    "servings, " +
//                    "ingredients)" +
//                    "values " +
//                    "("+id+"+ ','+
//                    "created_ts"+','+
//                    "updated_ts+"+','+
//                    "author_id+"+','+
//                    "cook_time_in_min"+','+
//                    "prep_time_in_min"+','+
//                    "total_time_in_min"+','+
//                    "title"+','+
//                    "cusine"+','+
//                    "servings")"+";"


            String sqlRecipe = String.format("insert into Recipie values (" +
                            "\"%s\", " +
                            "\"%s\", " +
                            "\"%s\", " +
                            "\"%s\", "+
                            "%d ," +
                            "%d, " +
                            "%d, " +
                            "\"%s\", "+
                            "\"%s\", " +
                            "\"%s\" " +
                            ");",
                    id,
                    created_ts,
                    updated_ts,
                    author_id,
                    cook_time_in_min,
                    prep_time_in_min,
                    total_time_in_min,
                    title,
                    cusine,
                    servings
            );

            for(int i = 0; i < ingredients.size(); i++){
                String sqlIngre = String.format("insert into Ingredients values(" +

                        "\"%s\" ,"+
                        "\"%s\" );",

                        id,
                        ingredients.get(i)
                        );

                stmt.execute(sqlIngre);
            }



            for(int i = 0; i < steps.size(); i++){
                String sqlSteps = String.format("insert into Steps values("+
                        "%d ,"+
                        "\"%s\" ,"+
                        "\"%s\" );",
                        steps.get(i).getPosition(),
                        steps.get(i).getItems(),
                        id
                        );
                stmt.execute(sqlSteps);
            }


            String sqlNutri = String.format("insert into NutritionInformation values("+
                    "\"%s\" ,"+
                    "%d ,"+
                    "%.2f ,"+
                    "%d ,"+
                    "%.2f ,"+
                    "%.2f );",
                    id,
                    recipe.getNutrition_information().getCalories(),
                    recipe.getNutrition_information().getCholesterol_in_mg(),
                    recipe.getNutrition_information().getSodium_in_mg(),
                    recipe.getNutrition_information().getCarbohydrates_in_grams(),
                    recipe.getNutrition_information().getProtein_in_grams()
            );


            stmt.execute(sqlRecipe);

            stmt.execute(sqlNutri);


        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }
    }

    public void deleteRecipe(Recipe recipe){
        Connection conn = null;
        Statement stmt = null;
        try{

            {conn = getConnection();
            stmt = conn.createStatement();
            String query = String.format("delete from Recipie" +
                            "where id = %d, ",
                    recipe.getId()
            );
            stmt.execute(query);}
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }

    }

    public void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
        try{
            if(rs!=null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(ps!=null){
                ps.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
//
//    public static void main(String[] args) throws SQLException
//    {
//
//        Connection cc=Sql.getConnection();
//
//        if(!cc.isClosed())
//
//            System.out.println("Succeeded connecting to the Database!");
//        Statement statement = cc.createStatement();
//        String sql = "select * from users";
//        ResultSet rs = statement.executeQuery(sql);
//        while(rs.next()) {
//            System.out.println(rs.getString("email")+"");
//        }
//
//
//
//    }
}


