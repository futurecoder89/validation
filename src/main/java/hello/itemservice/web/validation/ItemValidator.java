package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //item ==clazz
        //item == subItem
        // ==대신 isAssignableFrom()을 쓰는 이유는 자식들까지 다 해주기때문
    }
    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target; //casting  //Erros = BindingResult의 부모클래스
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName",
                "required");
        if (item.getPrice() == null || item.getPrice() < 1000 ||
                item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000},
                    null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000,
                        resultPrice}, null);
            }
        }
    }
}