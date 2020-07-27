package com.example.myapplication.container;

import android.os.StrictMode;

import com.example.myapplication.entity.Exercise;
import com.example.myapplication.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AvailableExerciseContainer {
    private static List<Exercise> exercises = new ArrayList<>();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    static{
       // init();
    }



    public static List<Exercise> getExercises(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String response = get("http://10.0.2.2:8081/exercise");
        try {
            List<Exercise> exercisesFromBackend = objectMapper.readValue(response, new TypeReference<List<Exercise>>(){});
            return exercisesFromBackend;
        } catch (IOException e) {
            e.printStackTrace();
            return exercises;
        }

    }

   /* public static void init(){
        exercises.add(new Exercise ("Приседания со штангой", "Ноги", R.drawable.legs, "Плавно согните ноги в коленях и присядьте так низко, насколько это возможно. Следите при этом за тем, чтобы спина не округлялась. Жёстко зафиксируйте её в прямом положении. Наиболее часто рекомендуют сгибать ноги до такого положения, когда бёдра оказываются параллельными полу. Если Вам удаётся присесть на максимальную глубину и ноги сгибаются максимально, тогда не допускайте внизу пружинящего движения (отбива).\n" +
                "\n" +
                "Опустившись вниз, сразу начните мощное обратное движение. Замедляйте движение в самом верхнем положении, чтобы штанга не начинала пружинить у Вас на плечах. Чётко зафиксируйте вертикальное положение. Затем снова начните плавно приседать. И так далее. Проделайте требуемое количество повторений."));
        exercises.add(new Exercise ("Жим штанги лёжа", "Грудь", R.drawable.chest, "Плавно опустите штангу вниз, сгибая руки. Гриф должен коснуться груди примерно на уровне сосков. Сразу после касания начните выжимать штангу вверх. Сосредоточьтесь на сокращении грудных мышц. При выжимании штанги не разгибайте локти до отказа. Это прямой путь к травме. Задержите штангу в верхней точке на мгновение, мысленно отметив напряжение в грудных мышцах. Снова плавно опустите штангу на грудь. И так далее.\n" +
                "\n" +
                "Не допускайте отбива. Гриф штанги не должен ударяться о грудь с целью спружинить и облегчить выжимание. Обязательно просите кого-нибудь проследить за Вами, пока Вы делаете упражнение. Особенно, если используете тяжёлые веса. Фиксируйте блины замками."));
        exercises.add(new Exercise ("Сгибания рук со штангой", "Бицепс", R.drawable.bice, "Исходное положение: встаньте прямо, штанга в опущенных руках. Хват супинированный. Локти плотно прижаты к телу.\n" +
                "\n" +
                "Плавно согните руки в локтях. Максимально сожмите бицепс в верхнем положении. Плавно разогните руки и сразу же снова начните их сгибать. И так далее.\n" +
                "\n" +
                "Обратите внимание на то, чтобы локти не двигались вперёд. Это важно для полноценного сокращения бицепса. Критерием правильного выполнения упражнения является невозможность расслабить бицепсы, когда руки со штангой согнуты.\n" +
                "\n" +
                "Не помогайте себе раскачиваниями телом. Если без этого Вы не можете проделать нужное количество повторений, значит вес штанги слишком велик."));
        exercises.add(new Exercise ("Подтягивания коленей к груди сидя", "Пресс", R.drawable.press, "Плавно подтяните колени к груди, сгибая ноги в коленях. Одновременно подайте вперёд тело, чтобы максимально сократить мышцы пресса. Задержитесь в таком сокращённом положении на секунду, затем плавно вернитесь в исходное положение. Проделайте нужное число повторений. Часто это количество может достигать 50-100."));

        exercises.add(new Exercise ("Жим штанги стоя — армейский жим", "Плечи", R.drawable.shoulders, "Исходное положение: стоя прямо, ноги на ширине плеч. Штанга удерживается на груди согнутыми руками. Штанга должна располагаться на уровне дельтовидных мышц и немного вынесена вперёд, чтобы при подъёме не задеть ею лицо. Хват пронированный (ладони смотрят вперёд). Ширина хвата примерно на 10 см шире плеч. Колени слегка согнуты. Используйте специальный тяжелоатлетический пояс на талию.\n" +
                "\n" +
                "Чтобы принять исходное положение, Вы можете либо брать штангу с пола и закидывать её на грудь, либо брать штангу с высоких стоек (примерно на уровне груди). Обязательно закрепляйте блины на штанге специальными замками.\n" +
                "\n" +
                "Аккуратно выжмите штангу вверх. При этом не разгибайте руки до конца. Локти должны остаться немного согнутыми. Задержитесь на мгновение в этом положении и плавно опустите штангу на грудь. Сразу же снова начните её выжимать. Рекомендуемое количество повторений – 10. Рекомендуемое количество подходов – 3-5."));


    }*/

    private static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        finally {
            response.close();
        }

    }
}
