/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe1;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RC_Student_lab
 */
public class Login {
   private String firstName;
   private String lastName;
   private String userName;
   private String password;
   private String cellNumber;
   
   public Login(String firstName, String lastName, String userName,String password, String cellNumber){
       this.firstName = firstName;
       this.lastName = lastName;
       this.userName = userName;
       this.password = password;
       this.cellNumber = cellNumber;
       
    
   }
   
   public String getfirstName(){
       return firstName;
   
   }
  
   public void setfirstName(String firstName){
       this.firstName = firstName;
   }
   
   public String getlastName(String lastName){
       return lastName;
   }
   
   public void setlastName(String lastName){
       this.lastName = lastName;
   }
   
   public String getuserName(String userName){
       return userName;
   }
   
   public void setuserName(String userName){
       this.userName = userName;
   }
   
   public String getpassword( String password){
       return password;
   }
   
   public void setpassword( String password){
       this.password = password;
   }
   
   public String getcellNumber(String cellNumber){
       return cellNumber;
   }
   
   public void setcellNumber(String cellNumber){
       this.cellNumber = cellNumber;
   }
   
   public boolean checkuserName(){
       return userName.contains("_")&&userName.length()<=5;
   }
   
   public void setuserName(){
       System.out.println(firstName + lastName);
   }
   
   public static boolean checkpasswordComplexity(String password){
       return password.length()>=8&&
               password.matches(".*[A-Z].*")&&
               password.matches(".*[a-z].*")&&
               password.matches(".*[0-9].*")&&
               password.matches(".*[!@#$%^&*()].*");
   }
   
   public static boolean checkcellPhoneNumber(String cellNumber){
       String regex = "^(\\+27|27|0)(6|7|8)[0-9](8)$";
       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(cellNumber);
       return matcher.matches();
   }
   
   public static String registerUser(String userName, String password){
       if (!checkuserName(userName)){
       return "username is not formatted correctly, please make sure to correct it ";    
       } else {
       }    
       if (!checkpasswordComplexity(password)){
         return "Password is not formatted correctly, please make sure to correct it ";  
           
       }
       return "Username successfully captured, Password successfully captured";
   }
   
   public boolean LoginUser( String userName, String password, String cellNumber){
       return userName.equals(this.userName)&& password.equals(this.password);
   }
   
   public String returnLoginStatus(String userName, String password, String cellNumber){
       if (LoginUser(userName, password, cellNumber)){
           return "Welcome"+ firstName + ","+lastName+" It is good to see you";
       }
       
       else{
           return "Username or Password incorrect, please try again.";
       }
   
   }
}
  

   
    

