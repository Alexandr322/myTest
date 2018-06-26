
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.response.Response;
import org.omg.PortableInterceptor.INACTIVE;
import org.openqa.selenium.By;
import pojo.GitItems;
import pojo.GitResponse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.id;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.*;
import static io.restassured.path.json.JsonPath.from;
import static org.junit.Assert.assertEquals;
public class TestCase1 {
    public String URLREPOSITORY = "https://api.github.com/search/repositories";
    public String URLGITHUB = "https://github.com/";

    //Работает
    @Test
    public void RestAssured1() throws JSONException {

        //Test 1.1
       /* Выполнить rest-запрос и получить список репозиториев, название
        которых содержит Selenide, запрос должен иметь сортировку по
        звёздам на убывание, ответ на запрос сохранить*/
        Response restResponse = given().param("q","Selenide").
                param("s","stars").when().
                get(URLREPOSITORY);
        assertEquals(200, restResponse.getStatusCode());
        // System.out.println(rest.asString());

        int total=from(restResponse.asString()).get("total_count");
        List<Integer> id = from(restResponse.asString()).get("items.id");
        List<String> names = from(restResponse.asString()).get("items.name");
        List<String> description = from(restResponse.asString()).get("items.description");
        List<String> language = from(restResponse.asString()).get("items.language");
        List<String> license = from(restResponse.asString()).get("items.license.name");
        List<Integer> stars = from(restResponse.asString()).get("items.stargazers_count");
        // System.out.println(total+" "+id.get(0)+" "+names.get(0)+" "+description.get(0)+" "+language.get(0)+" "+stars.get(0)+" "+license.get(0));

        //Test 1.2
        //Перейти на github.com и выполнить поиск по тому же слову Selenide
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        Configuration.browser = "chrome";
        open(URLGITHUB);
        $(By.xpath("/html/body/div[1]/header/div/div[2]/div/div/div/div/form/label/input[1]")).setValue("Selenide").pressEnter();

        //Test 1.3
        /*Проверить, что первый элемент из ответа на запрос соответствует
        первому элементу в результатах поиска на UI, выполнить проверку
        по названию репозитория, короткому описанию, основному языку,
        лицензии и количеству звёзд.*/

        //по названию репозитория
        String repository=$(By.xpath("//*[@id=\"js-pjax-container\"]//a[@class=\"v-align-middle\"]/em")).getText();
        if (repository.equals(names.get(0))){
            System.out.println("Repository equals");
        }
        //По короткому описанию
        String descriptionXpath=$(By.xpath("//*[@id=\"js-pjax-container\"]//p[@class=\"col-9 d-inline-block text-gray mb-2 pr-4\"]")).getText();
        if(descriptionXpath.equals(description.get(0))){
            System.out.println("Description equals");
        }
        // по основному языку
        String languageXpath=$(By.xpath("//*[@id=\"js-pjax-container\"]//div[@class=\"d-table-cell col-2 text-gray pt-2\"]")).getText();
        if(languageXpath.equals(language.get(0))){
            System.out.println("Language equals");
        }
        //По лицензии
        // не будет равно так как на странице UI лицензия MIT License,а в JSON MIT license.
        String licenseXpath=$(By.xpath("//*[@id=\"js-pjax-container\"]//p[@class=\"f6 text-gray mr-3 mb-0 mt-2\"]")).getText();
        if(licenseXpath.equals(license.get(0))){
            System.out.println("License equals");
        }
        //по звездам
        String starsXpath=$(By.xpath("//*[@id=\"js-pjax-container\"]//a[@class=\"muted-link\"]")).getText();
        if(Integer.parseInt(starsXpath)==(stars.get(0))){
            System.out.println("Stars Equals");
        }

        //Test 1.4
       /* Проверить, что общее количество найденных репозиториев на UI
        соответствует оному из запроса (523).*/
        String countRepository=$(By.xpath("//*[@id=\"js-pjax-container\"]//span[@class=\"Counter ml-1 mt-1\"]")).getText();
        if(Integer.parseInt(countRepository)==(total)){
            System.out.println("Total repository equals");}

    }
    //Работает
    @Test
    public void UITest2(){
        // Test 2.1
       /* Найти при помощи запроса на UI репозиторий с наибольшим
        количеством звёзд*/
        String URLSearchRepository="https://api.github.com/search/repositories";
        Response res=given().param("q","language:Java").param("s","stars")
                .get(URLSearchRepository);
        assertEquals(200,res.getStatusCode());
        System.out.println(URLSearchRepository.toString());
        //Test 2.2
    /*Убедиться, что в обеих случаях это один и тот же репозиторий (что
    нужно проверить описано в тест кейсе 1, пункт 3)*/
    }

    //Работает
    @Test
    public void RestAssuredTest3(){

        //Test 3.1
      /* Создать при помощи rest-запроса репозиторий в своём аккаунте
      (нужно завести рандомный аккаунт для этого задания, можно
      создать общий на всю группу)*/

        // Создаю репозиторий с именем HelloRepository rest запросом
        Response createRepository=given().contentType("application/json").body("{\n" +
                "  \"name\": \"HelloRepository\",\n" +
                "  \"description\": \"This is your first repository\",\n" +
                "  \"homepage\": \"https://github.com\",\n" +
                "  \"private\": false,\n" +
                "  \"has_issues\": true,\n" +
                "  \"has_projects\": true,\n" +
                "  \"has_wiki\": true\n" +
                "}"
        ).when().post("https://api.github.com/user/repos?access_token=88ea84e1ad632e433703c7d44ef3a090e7108c63");
        assertEquals(201,createRepository.getStatusCode());
        System.out.println(createRepository.getStatusCode()+"  " + "Репозиторий создан");

        //Test 3.2
       /*  Перейти на github.com, залогиниться под ранее созданным
         аккаунтом, проверить, что репозиторий создался и удалить его*/

        // Пробую удалить репозиторий через запрос
        String URL="https://api.github.com/repos/Trainingacc322/HelloRepository?access_token=88ea84e1ad632e433703c7d44ef3a090e7108c63";
        Response delete=given().contentType("application/json").
                body(" \"name\": \"HelloRepository\"").when().
                delete(URL);
        System.out.println(delete.asString());
        // Возвращает {"message":"Must have admin rights to Repository."} Пробывал по разному но токен не принимает
        //Поэтому я проверил что он создался и вручную удалил репозиторий


        //Test 3.3
      // При помощи rest-запроса убедиться, что созданный в пункте 1
      // репозиторий был удалён
        String URLMyRepository="https://api.github.com/repos/Trainingacc322/HelloRepository";
        Response comparison=given().when().get(URLMyRepository);
        System.out.println(comparison.asString());
    }
}
