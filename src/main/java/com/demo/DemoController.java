package com.demo;

import com.jlack.*;
import com.jlack.entities.SlackEntity;
import com.jlack.handlers.MessageEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class DemoController {

    private SlackApp slackApp;

    public DemoController() {
        this.slackApp = new SlackApp();

        slackApp.event(new MessageEventHandler() {
            @Override
            public void handle(Event event, AppContext context) {
                SlackEntity entity = event.getEntity();
                System.out.println("Inside Event function");
                System.out.println("Got message: " + entity.event.text);
                System.out.println("Got type: " + entity.type);
                System.out.println("Got event type: " + entity.event.type);
                System.out.println(entity.event.ts);
                System.out.println(entity.event.event_ts);
            }
        });

        slackApp.on("on string test", new MessageEventHandler() {
            @Override
            public void handle(Event event, AppContext context) {
                SlackEntity entity = event.getEntity();
                System.out.println("Got message: " + entity.event.text);
                System.out.println("Got type: " + entity.type);
                System.out.println("Got event type: " + entity.event.type);
                System.out.println("Got event type: " + entity.event.channel);

                event.reply("from on string test");
            }
        });

        slackApp.on(Pattern.compile("on pattern (.*)"), new MessageEventHandler() {
            @Override
            public void handle(Event event, AppContext context) {
                Matcher matcher = context.getMessagePattern().matcher(event.getEntity().event.text);
                if (matcher.matches()) {
                    String match = matcher.group(1);
                    event.reply("pattern test: " + match);
                } else {
                    event.reply("No match to pattern");
                }
            }
        });
    }

    @RequestMapping("/")
    public String index() {
        return "Slack framework test online!";
    }

    @PostMapping("/slack/events")
    public ResponseEntity<String> getEvents(@RequestBody SlackEntity entity, @RequestHeader Map<String, String> headers) {
        // TODO figure out how to integrate into web frameworks
        slackApp.setHeaders(headers);
        String result = slackApp.run(entity);
        System.out.println("Result: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
