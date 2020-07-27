package com.example.myapplication.data;

import com.example.myapplication.container.UserContainer;
import com.example.myapplication.data.model.LoggedInUser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        /*try {
            LoggedInUser user = UserContainer.getUser();
            LoggedInUser admin = UserContainer.getAdmin();

            if(user.getDisplayName().equals(username)){
                user.setLogged(true);
                return new Result.Success<>(user);
            }


            if(admin.getDisplayName().equals(username)){
                admin.setLogged(true);
                admin.setAdmin(true);
                return new Result.Success<>(admin);
            }


            else return null;


        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }*/

        try {
            LoggedInUser user = new LoggedInUser("3", username, true);
            String result2 = post("http://10.0.2.2:8081/login", getJson(username, password));
            if (username.equals("Admin"))
            {
                if (result2.equals("ok") ){
                    UserContainer.setNewDataAdmin(username, true);
                    return new Result.Success<>(user);
                }

                else return new Result.Error(new Exception("Wrong credentials"));
            }
            else {
                if (result2.equals("ok") ){
                    UserContainer.setNewDataUser(username, true);
                    return new Result.Success<>(user);
                }

                else return new Result.Error(new Exception("Wrong credentials"));
            }

            //return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

    }

    @NotNull
    private String getJson(String username, String password) {
        return "{" +
                "\"login\": \"" + username + "\"," +
                "\"password\": \"" + password + "\"" +
                "}";
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            response.close();
        }


    }

    public void logout() {
        // TODO: revoke authentication
    }
}
