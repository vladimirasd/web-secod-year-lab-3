import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;


//Я люблю запятые ;___;
@FacesConverter("BigDecimalConverter")
public class BigDecimalConvertor implements Converter {


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {

        if (s == null || s.trim().isEmpty()) {
            return null;
        }

        try {
            return new BigDecimal(s.replace(",", ".").trim());
        } catch (Exception e) {
            FacesMessage msg =
                    new FacesMessage("Ошибка конвертации",
                            "Введите число");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {

        return o == null ? "" : o.toString();

    }
}
