package com.casamento.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MarkList {
    private Properties properties = new Properties();

    public void updatePessoa(String newPessoa, int quantidade){
        this.properties.getPessoa().addText();
        this.properties.getPessoa().getRichText().get(0).getText().setContent(newPessoa);
        this.properties.getQtde().setNumber(quantidade);
    }

    @Getter
    @Setter
    private static class Properties {
        @JsonProperty("Marcado")
        private Marcado marcado = new Marcado();
        @JsonProperty("Pessoa")
        private Pessoa pessoa = new Pessoa();
        @JsonProperty("Qtde")
        private Qtde qtde = new Qtde();

        @Getter
        @Setter
        private static class Qtde {
            private int number = 0;
        }

        @Getter
        @Setter
        private static class Marcado {
            private Boolean checkbox = true;
        }

        @Getter
        @Setter
        private static class Pessoa {
            @JsonProperty("rich_text")
            private List<RichText> richText = new ArrayList<>();

            public void addText(){
                richText.add(new RichText());
            }

            @Getter
            @Setter
            private static class RichText {
                private Text text = new Text();

                @Getter
                @Setter
                private static class Text {
                    private String content;
                }
            }
        }
    }

}
