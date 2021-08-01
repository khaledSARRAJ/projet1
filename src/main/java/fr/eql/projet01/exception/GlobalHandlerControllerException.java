package fr.eql.projet01.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.bnguimgo.springbootrestserver"} )
public class GlobalHandlerControllerException extends ResponseEntityExceptionHandler{

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		//Vous pouvez initialiser n'importe quelle donnée ici
	}
	
	@ModelAttribute
    public void globalAttributes(Model model) {
		model.addAttribute("technicalError", "Une erreur technique est survenue !");
    }
	
    @ExceptionHandler(Exception.class)//toutes les autres erreurs non gérées par le service sont interceptées ici
    public ResponseEntity<AecResourceExceptionDTO> unknowError(HttpServletRequest req, Exception ex) {
        AecResourceExceptionDTO response = new AecResourceExceptionDTO();
        response.setErrorCode("Technical Error");
        response.setErrorMessage(ex.getMessage());
        response.setRequestURL(req.getRequestURL().toString()); 
        return new ResponseEntity<AecResourceExceptionDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AecResourceException.class)
    public ResponseEntity<AecResourceExceptionDTO> aecResourceError(HttpServletRequest req, AecResourceException ex) {
        AecResourceExceptionDTO aecResourceExceptionDTO = new AecResourceExceptionDTO();
        aecResourceExceptionDTO.setStatus(ex.getStatus());
        aecResourceExceptionDTO.setErrorCode(ex.getErrorCode());
        aecResourceExceptionDTO.setErrorMessage(ex.getMessage());
        aecResourceExceptionDTO.setRequestURL(req.getRequestURL().toString()); 
        return new ResponseEntity<AecResourceExceptionDTO>(aecResourceExceptionDTO, ex.getStatus());
    }
}