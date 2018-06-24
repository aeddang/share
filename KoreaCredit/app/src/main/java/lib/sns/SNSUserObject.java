package lib.sns;


public class SNSUserObject {
    public String userToken = "";
    public String userID = "";
    public String userSEQ = "";
    public String userName = "";
    public String userImg = "";
    public String userBirthday = "";

    public void logout() {

        userToken = "";
        userID = "";
        userSEQ = "";
        userName = "";
        userImg = "";
        userBirthday = "";
    }
    public Boolean isLogin() {


        if(userSEQ.equals("")==true){
            return false;

        }else{

            return true;
        }


    }
    public String toString() {
        return "userToken:"+ userToken+" userID:"+userID+" userSEQ:"+userSEQ+" userName:"+userName+" userImg:"+userImg+" userBirthday:"+userBirthday;


    }
}

