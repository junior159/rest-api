package com.example.restapi.controller;

import com.example.restapi.model.Contribution;
import com.example.restapi.serviceImpl.ContributionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.logging.Logger;

@RestController
@RequestMapping("/contributions")
public class ContributionController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ContributionServiceImpl contributionService;

    //вывод всех вкладов
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Contribution>> getContributions(@ModelAttribute Contribution contribution, @SortDefault(sort = "id") Sort sort){
        return new ResponseEntity<>(contributionService.getAll(contribution,sort), HttpStatus.OK);
    }

    //вывод вклада по id
    @RequestMapping(value = "//{id}",method = RequestMethod.GET)
    public ResponseEntity<Contribution>getContribution (@PathVariable long id){
        Contribution contribution = contributionService.getById(id);

        if (contribution != null){
            return new ResponseEntity<>(contributionService.getById(id),HttpStatus.OK);
        }else{
            logger.severe("ContributionId "+ id + " is not existed");
            return new ResponseEntity<>((Contribution) null, HttpStatus.NOT_FOUND);
        }
    }

    // добавление вклада
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<?> addContribution(@RequestBody Contribution contribution){
        return new ResponseEntity<>(contributionService.save(contribution),HttpStatus.CREATED);
    }

    // обновление вклада
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseEntity<?>updateContribution(@RequestBody Contribution contribution){
        return new ResponseEntity<>(contributionService.update(contribution),HttpStatus.UPGRADE_REQUIRED);
    }

    // удаление вклада по id
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContribution(@PathVariable long id){

        Contribution contribution =contributionService.getById(id);

        if (contribution!=null){
            contributionService.remove(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else{
            logger.severe("Did not delete the object. ContributionId " + id + " is not existed");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }





}
