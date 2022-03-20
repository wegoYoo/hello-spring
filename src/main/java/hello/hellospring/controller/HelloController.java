package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping ("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");

        /* 렌더링 페이지 설정
        *   resources > templates > hello 파일 찾음
        *   컨트롤러에서 리턴값으로 문자를 반환하면 뷰 리졸버(viewResolver)가 화면을 찾아서 처리한다.
        *   'resources:templates/' +{ViewName} + '.html'
        *   'spring-boot-devtools' 라이브러리를 추가하면, 'html' 파일을 컴파일만 해주면 서버 재시작 없이 View 파일 변경이 가능하다.
        *   인텔리J 컴파일 방법 : 메뉴 build > Recompile */
        return "hello";
    }

        /* ls -arlth
        * 1. cmd 에서
        *   C:\Users\vaims\InteliJ\hello-spring 진입
        * 2. build
        *   gradlew.bat build
        * 3. java로 실행해본다
        *   3.1. build 파일이 있는 경로로 이동
        *       C:\Users\vaims\InteliJ\hello-spring\build\libs 에 빌드 파일이 쌓인다.
        *   3.2. 해당 파일 실행
        *       java -jar * (* = 빌드한 파일명)
        *       java 끄기 = Ctrl + c
        * 4. localhost:8080으로 확인
        * 5. 배포시
        *   빌드한 *.jar 파일만 폴더에 넣고 java -jar 실행만 시켜주면 된다.
        *   그러면 서버에서 spring 작동함
        *
        * * 번외. (빌드가 잘 안될때)
        *   2번으로 돌아가서 gradlew clean OR gradle clean build
        *   build 폴더가 없어진다.
        * */

    @GetMapping("hello-mvc")
/*    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model){*/
    public String helloMvc(@RequestParam("name") String name, Model model){
        /* URL 에 GET 값을 넣어주어야 에러 안뜸.
        * localhost:8080/hello-mvc?name=sookie */

        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello "+name; // "hello spring"
    }

    //데이터를 받을때 API방식을 많이 사용한다.
    /* json 방식으로 key값과 value 값을 확인 할 수 있다.
    *  객체를 반환하고 @ResponseBody 라고 해놓으면 json 형식으로 반환한다.
    *  결과 : {"name":"sookie"} */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){ // Hello 라는 객체
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello {
        private String name;

        /* java bean 표준 방식 / property 방식
        * Getter & Setter, 생성자 와 같은 코드 생성 (Alt + Fn + Delete / Alt + Insert)
        * */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
/*
* 클래스 관계
* > MemberService
* > Interface : 아직 데이터 저장소가 선정되지 않아서 interface를 사용함(가상의 시나리오)
*   MemberRepository
*   MemoryMemberRepository (RDB/NoSQL등)
* */

