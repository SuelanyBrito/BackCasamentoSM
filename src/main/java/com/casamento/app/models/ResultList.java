package com.casamento.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultList {
    @JsonProperty("results")
    private List<Results> results;

    @Getter
    @Setter
    public static class Results {

        @JsonProperty("rich_text")
        public RichText richText = new RichText();

        @Getter
        @Setter
        public static class RichText{
            @JsonProperty("text")
            private Text text = new Text();

            @Getter
            @Setter
            public static class Text {
                @JsonProperty("content")
                private String content;
            }
        }


    }
}
