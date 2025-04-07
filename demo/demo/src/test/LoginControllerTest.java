@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {


   @Autowired
   private MockMvc mockMvc;


   @MockBean
   private LoginService loginService;


   @Test
   public void testSuccessfulLogin() throws Exception {
       Login user = new Login();
       user.setUsername("admin");
       when(loginService.log(anyString(), anyString())).thenReturn(user);
       mockMvc.perform(post("/login")
               .param("username", "admin")
               .param("password", "password"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/welcome"));
   }


   @Test
   public void testFailedLogin() throws Exception {
       when(loginService.log(anyString(), anyString())).thenReturn(null);
       mockMvc.perform(post("/login")
               .param("username", "invalidUser")
               .param("password", "wrongPass"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().attributeExists("error"));
   }
}
