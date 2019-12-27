package my.webapplication.board.controller;

import my.webapplication.board.domain.Article;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BoardControllerTestUtils {
    // 하나의 article 테스트 인스턴스 반환
    static Article makeOneArticle(int index){
        Article article = new Article(1,
                "title"+index,
                "userName"+index,
                "password"+index,
                "content"+index,
                LocalDateTime.of(1989,12,11,0,0,index),
                0);
        return article;
    }

    // 하나 이상의 article 테스트 인스턴스를 가진 리스트 반환
    static List<Article> makeArticles(int size) {

        return Stream.iterate(1, i -> i+1)
                .limit(size)
                .map(i -> new Article(
                        i,
                        "title"+i,
                        "userName"+i,
                        "password"+i,
                        "content"+i,
                        LocalDateTime.of(2019,12,11,0,0,i),
                        0
                )).collect(toList());
    }



    /*
    List<Article> -> String(JSON format)
     */
    public static String articleListToJson(List<Article> articles) throws InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        HashMap<String, Method> getPropertyMethodMap = makeGetPropertyMethodMap();

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            sb.append("{");

            Iterator<Map.Entry<String, Method>> iterator = getPropertyMethodMap.entrySet().iterator();
            while(iterator .hasNext()) {
                Map.Entry<String, Method> entry = iterator.next();
                String key = wrapDoubleQuote(entry.getKey());
                Method method = entry.getValue();

                if(key.equals("\"dateTime\"")){
                    LocalDateTime dateTime = (LocalDateTime)method.invoke(article);
                    sb.append(key + ":" + wrapDoubleQuote(dateTime.toString()) + ",");
                    continue;
                }

                if(key.equals("\"id\"") || key.equals("\"viewCount\"")){
                    sb.append(key + ":" + method.invoke(article) + ",");
                    continue;
                }

                sb.append(key + ":" + wrapDoubleQuote(method.invoke(article).toString()) + ",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("},");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");

        return sb.toString();
    }

    @Test
    public void test() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.out.println(articleListToJson(makeArticles(2)));
    }
    /*
    Article -> String(JSON)
     */
    public static String articleToJson(Article article) throws InvocationTargetException, IllegalAccessException {

        HashMap<String, Method> getPropertyMethodMap = makeGetPropertyMethodMap();
        Iterator<Map.Entry<String, Method>> iterator =
                getPropertyMethodMap.entrySet().iterator();

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        while(iterator .hasNext()) {
            Map.Entry<String, Method> entry = iterator.next();
            String key = wrapDoubleQuote(entry.getKey());
            Method method = entry.getValue();

            if(key.equals(wrapDoubleQuote("dateTime"))){
                LocalDateTime dateTime = (LocalDateTime)method.invoke(article);
                sb.append(key + ":" + wrapDoubleQuote(dateTime.toString()) + ",");
                continue;
            }

            if(key.equals(wrapDoubleQuote("id"))
                    || key.equals(wrapDoubleQuote("viewCount"))){
                sb.append(key + ":" + method.invoke(article) + ",");
                continue;
            }

            sb.append(key + ":" + wrapDoubleQuote(method.invoke(article).toString()) + ",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

    @Test
    public void testArticleToJSON() throws InvocationTargetException, IllegalAccessException {
        Article article = makeOneArticle(1);

        System.out.println(articleToJson(article));
    }

    private static String wrapDoubleQuote(String str) {
        return "\"" + str + "\"";
    }

    /*
    설명
    Article 도메인이 가진 메서드들 중 get[프로퍼티] 메서드를
    Key : 프로퍼티명 , value : 프로퍼티 메서드 로 그룹화한 맵을 반환한다.

     */
    private static HashMap<String, Method> makeGetPropertyMethodMap() {

        List<Method> methods = List.of(Article.class.getMethods());

        HashMap<String, Method> methodHashMap = methods.stream()
                .filter(m -> m.getName().startsWith("get") && !m.getName().endsWith("Class"))
                .collect(
                        HashMap::new,
                        (container, method)-> container.put(makePropertyKey(method), method),
                        HashMap::putAll
                );
        return methodHashMap;
    }

    @Test
    public void testMakeGetPropertyMethodMap(){
        Map<String, Method> map = makeGetPropertyMethodMap();
        System.out.println(map);
    }


    /*
     parameter
     - method : get[프로퍼티명] 의 메서드를 참조하고 있다.

     return 메서드명에서 앞에 get을 제외한 String 값을 반환한다.
     */
    private static String makePropertyKey(Method method) {
        String propertyName = method.getName().substring(3);
        String first_lower = String.valueOf(propertyName.charAt(0)).toLowerCase();

        return propertyName.replaceFirst("\\S", first_lower);
    }

}
