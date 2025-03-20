package ink.labrador.mmsmanager.domain;

import ink.labrador.mmsmanager.constant.CaptchaContentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Captcha {
    private static final Character[] NUMBER_DICT = new Character[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private static final Character[] CHAR_DICT = new Character[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final Character[] MIX_DICT = new Character[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final Character[] OPERATOR_DICT = new Character[] { '+', '-', '*', '/' };

    private final String content;
    private final String answer;
    private final String id;
    private final CaptchaContentType contentType;

    private Captcha(CaptchaContentType captchaContentType, int size) {
        this.id = UUID.randomUUID().toString();
        if (captchaContentType == CaptchaContentType.ALPHABET_ONLY) {
            this.content = getRandomSegment(CHAR_DICT, size);
            this.answer = this.content;
        } else if (captchaContentType == CaptchaContentType.NUMBER_ONLY) {
            this.content = getRandomSegment(NUMBER_DICT, size);
            this.answer = this.content;
        } else if (captchaContentType == CaptchaContentType.MIXED_CHARACTER) {
            this.content = getRandomSegment(MIX_DICT, size);
            this.answer = this.content;
        } else {
            Map<String, String> map = getRandomExpression();
            this.content = map.get("expression");
            this.answer = map.get("answer");
        }
        this.contentType = captchaContentType;
    }

    public static Captcha init(CaptchaContentType captchaContentType, int size) {
        return new Captcha(captchaContentType, size);
    }

    private String getRandomSegment(Character[] characters, int size) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < size; i ++) {
            int randomIndex = random.nextInt(characters.length);
            sb.append(characters[randomIndex]);
        }
        return sb.toString();
    }

    private Map<String, String> getRandomExpression() {
        Map<String, String> expression = new HashMap<>();
        SecureRandom random = new SecureRandom();
        int i1 = random.nextInt(9);
        int i2 = random.nextInt(9);
        Character operator = OPERATOR_DICT[random.nextInt(OPERATOR_DICT.length)];
        if (operator == '+') {
            expression.put("expression", joinExpression(i1, i2, operator));
            expression.put("answer", String.valueOf(i1 + i2));
        } else if (operator == '-') {
            expression.put("expression", joinExpression(i1, i2, operator));
            expression.put("answer", String.valueOf(i1 - i2));
        } else if (operator == '*') {
            expression.put("expression", joinExpression(i1, i2, operator));
            expression.put("answer", String.valueOf(i1 * i2));
        } else {
            while (i1 == 0) {
                i1 = random.nextInt(9);
            }
            int result = i1 * i2;
            expression.put("expression", joinExpression(result, i1, operator));
            expression.put("answer", String.valueOf(i2));
        }
        return expression;
    }

    private String joinExpression(int i1, int i2, Character operation) {
        return String.valueOf(i1) + operation + i2 + "=?";
    }
}
