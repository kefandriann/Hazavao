package com.hazavao.app.endpoint.rest.controller.health;

import com.hazavao.app.PojaGenerated;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PojaGenerated
@RestController
@AllArgsConstructor
public class HazavaoController {

    public final ChatGPTController chatgpt = new ChatGPTController();
    public static final ResponseEntity<String> OK = new ResponseEntity<>("OK", HttpStatus.OK);
    public static final ResponseEntity<String> KO =
            new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);

    @GetMapping("/hazavao")
    public String definir(@RequestParam String teny) {
        String prompt = "Tu es un dictionnaire pédagogique malgache. Farito ny teny '"+teny+"' amin'ny teny malagasy amina fehezanteny iray tokana fotsiny.";

        try {
            return chatgpt.executePrompt(prompt);
        } catch (Exception e) {
            return "Error : " + e.getMessage();
        }
    }
}
