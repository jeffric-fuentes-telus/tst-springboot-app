package com.telus.iof.itss.api.controllers;

import com.telus.iof.itss.api.model.PushNotificationRequest;
import com.telus.iof.itss.api.model.dto.FieldUpdateRequest;
import com.telus.iof.itss.api.model.dto.ITSSUserHTTPRequest;
import com.telus.iof.itss.api.model.dto.catalogs.Catalog1Value;
import com.telus.iof.itss.api.model.entities.ITSSHATEOASEntity;
import com.telus.iof.itss.api.model.entities.ITSSRegistry;
import com.telus.iof.itss.api.services.ITSSService;
import com.telus.iof.itss.api.services.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ITSSController {
    @Autowired
    ITSSService itssService;
    @Autowired
    PushNotificationService pushNotificationService;

    @GetMapping("/Users/{userId}/ITSS")
    public @ResponseBody
    ResponseEntity getList(@PathVariable String userId, @RequestParam("from") Optional<Integer> from, @RequestParam("to") Optional<Integer> to ){
        ArrayList response=null;
        try{

            ITSSUserHTTPRequest userHTTPRequest = new ITSSUserHTTPRequest();
            userHTTPRequest.setUserId(userId);
            if(from.isPresent() && to.isPresent()){
                response= itssService.getITSSRelatedToUserPaginated(userHTTPRequest, from.get(), to.get());
            }else
            response = itssService.getRelatedITSSHateoasByUser(userHTTPRequest);


            return ResponseEntity.ok(response);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An Error Ocurred Procesing Your Request");
        }
    }

    @GetMapping("/GetAll")
    ResponseEntity getAllItss(@RequestParam("from") Optional<Integer> from, @RequestParam("to") Optional<Integer> to, @RequestParam Optional<String> pattern, @RequestParam Optional<Boolean> rawData ){
       if(pattern.isPresent()){
           ArrayList l= itssService.getITSSByPattern(pattern.get());
           return ResponseEntity.status(HttpStatus.OK).body(l);
       }
       if(from.isPresent() && to.isPresent()) return ResponseEntity.status(HttpStatus.OK).body( itssService.GetITSSPaginated(from.get(), to.get()));
       if(rawData.isPresent() && rawData.get().booleanValue()) return ResponseEntity.status(HttpStatus.OK).body( itssService.getAll());
       else return ResponseEntity.status(HttpStatus.OK).body( itssService.getAllITSSHateoas());
    }

    @GetMapping("/ITSS/{id}")
    public ITSSRegistry getRegistryById(@PathVariable String id){

        return itssService.getITSSById(id);
    }

    @GetMapping("/sendData")
    public String sendData(){
        PushNotificationRequest pushNotificationRequest=new PushNotificationRequest();
        pushNotificationRequest.setMessage("This is a test");
        pushNotificationRequest.setToken("ciXW0dMuhuA:APA91bGyu7g_YzbTe4ggFFYfHpj5o7R7Xj6xjjxj3qnPlMKwsV6K2IsXsmT4kD_k4p_xWgnaSe-PwYqhZWc_2LfS769tF_oWW2t6MXsheLdcjN8g52OcommcD1V0YNXwlaOmq4xIytAH");
        pushNotificationRequest.setTitle("Newer");
        pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
        return "ok";

    }

    @PutMapping("/ITSS/{itssId}")
    public ResponseEntity updateITSSField(@PathVariable String itssId, @RequestBody FieldUpdateRequest request){
        Boolean isUpdated = itssService.updateITSSProperty(request.getUserId(), itssId,request.getField(), request.getOldValue(), request.getNewValue() );
        if (isUpdated){

            return  ResponseEntity.status(HttpStatus.OK).body("Successfully Updated");
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Request Not Valid");
        }
    }

    @GetMapping("/ITSS/Catalogs/Catalog1")
    public ResponseEntity getCatalog1Values(){
        List<Catalog1Value> values = itssService.getCatalog1();
        if(values.isEmpty()){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot Provide Catalog 1 Values, Please Contact Administrator.");
        }else{
           return ResponseEntity.ok(values);
        }
    }
}
