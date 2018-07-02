package com.forestnewark.Library.Manager.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forestnewark.Library.Manager.Repository.CompositionRepository;
import com.forestnewark.Library.Manager.bean.Composition;
import com.forestnewark.Library.Manager.service.FileService;

@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/composition")
@CrossOrigin(origins = "*")
@org.springframework.web.bind.annotation.RestController
public class CompositionController {

	@Autowired
	CompositionRepository compositionRepository;
	
	@Autowired
	FileService fileService;

    /**
     * Get all compositions
     * @return all compositions (unsorted)
     */

    @RequestMapping(value= "/getAll", produces = "application/json")
    public ResponseEntity<?> getAllCompositions(){
    	return new ResponseEntity<>(compositionRepository.findAll(),HttpStatus.OK);
     
    }

    /**
     * Add New Composition
     * @param composition
     * @return added composition
     */

    @RequestMapping(value="/composition/add", method= RequestMethod.POST)
    public Composition addNewComposition(@RequestBody Composition composition){
        compositionRepository.save(composition);
        return composition;

    }

    /**
     * Edit composition
     * @param composition
     * @return
     */
    @RequestMapping(value="/composition/update", method = RequestMethod.POST)
    public Composition editComposition(@RequestParam("userName") String userName,@RequestParam("composition") String composition){

        Composition compToUpdate = null;
        try {
            compToUpdate = new ObjectMapper().readValue(composition,Composition.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        compToUpdate.setEditedBy(userName);
        compositionRepository.save(compToUpdate);
        return compToUpdate;
    }


    /**
     * Delete Composition
     * @param composition
     * @return
     */
    @RequestMapping(value="/composition/delete", method= RequestMethod.POST)
    public Composition deleteComposition(@RequestBody Composition composition){
        compositionRepository.delete(composition);
        return composition;
    }
    
    @RequestMapping(value="/util/catagory",method= RequestMethod.GET)
    public ResponseEntity<?> getAllCatagories(){

        return new ResponseEntity<Object>(compositionRepository.findDistinctCatagory(),HttpStatus.OK);

    }
    
    /**
     * Upload CSV File
     * @param file
     * @throws IOException
     */
    @RequestMapping(value = "/submitCSV", method = RequestMethod.POST)
    public void uploadFileHandler(@RequestParam("File") MultipartFile file,@RequestParam("userName") String userName) throws IOException {

        compositionRepository.deleteAll();

        Reader in = new FileReader(fileService.convert(file));
        //CSVFormat csvFileFormat = CSVFormat.DEFAULT.withQuote();

        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("Catagory", "libnum", "Title","Composer","Arranger","Copyright","Ensemble","Notes").withQuote('"').parse(in);
        for (CSVRecord record : records) {
            Composition composition = new Composition(
                    record.get("Catagory").trim(),
                    record.get("libnum"),
                    validateInput(record.get("Title")),
                    validateInput(record.get("Composer")),
                    validateInput(record.get("Arranger")),
                    record.get("Ensemble"),
                    record.get("Copyright"),
                    validateInput(record.get("Notes")),
                    null,
                    userName
            );
            System.out.println("Catagory::" + record);
            if(!composition.getCatagory().equals("Catagory")){
                compositionRepository.save(composition);
            }

        }
    }
    
    public String validateInput(String input){
//      if(input.contains("\"") || input.contains(",") || input.contains("'") || input.contains("(") || input.contains(")") || input.contains(";")){
//          input+= "\""+input+"\"";
//          return input;
//      }
      return input;
  }
    
    @RequestMapping(value = "/downloadCSV")
    public void downloadCSV(HttpServletResponse response) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        response.setContentType("text/csv");
        String reportName = "Band-Library-("+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"))+").csv";
        response.setHeader("Content-disposition", "attachment;filename="+reportName);

        ArrayList<String> rows = new ArrayList<String>();
        rows.add("Catagory,libnum,Title,Composer,Arranger,Ensemble,Copyright,Notes,URL");
        rows.add("\n");
        List<Composition> compositions = compositionRepository.findAll();

        for (Composition comp: compositions) {
            String line= validoutput(comp.getCatagory()) + "," +
                         validoutput(comp.getLibraryNumber()) + "," +
                         validoutput(comp.getTitle())  + "," +
                         validoutput(comp.getComposer())  + "," +
                         validoutput(comp.getArranger())  + "," +
                         validoutput(comp.getEnsemble()) + "," +
                         validoutput(comp.getCopyright()) + "," +
                         validoutput(comp.getNotes())  + "," +
                         validoutput(comp.getUrl()) + "," +
                        "\n";
            rows.add(line);
            System.out.println(line);
        }

        Iterator<String> iter = rows.iterator();
        while (iter.hasNext()) {
            String outputString = (String) iter.next();
            response.getOutputStream().print(outputString);
        }

        response.getOutputStream().flush();

    }

    public String validoutput(String output){

        if (output == null){
           return "\"\"";
        }
        String modOutput = "\""+output+"\"";
        return modOutput;
    }


}
