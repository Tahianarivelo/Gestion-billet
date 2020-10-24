/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation2.evaluation2;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import modele.Avion;
import modele.Billet;
import modele.Billet_Affiche_PDF;
import modele.Billet_detail;
import modele.ConditionTarif;
import modele.Constant;
import modele.Detail_Billet_PDF;
import modele.Reduction;
import modele.Tarif;
import modele.Trajet;
import modele.Trajet_detail;
import modele.Ville;
import modele.Vol;
import modele.Vol_Affiche_PDF;
import modele.Vol_detail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import outil.UG;
import pdf.ExportPDF;
import service.ServiceBillet;
import service.ServiceTarif;
import service.ServiceTrajet;
import service.ServiceVol;

/**
 *
 * @author tahiana
 */
@Controller
public class ControlerWEB {
    
    @GetMapping("Dashboard")
    public String Dashboard(){
        return "Dashboard";
    }
    
    @GetMapping("ListTrajet_Get")
    public String ListTrajet(Model model,@RequestParam(required = false) Integer page,@RequestParam(required = false) String colonne){
        String res = "ListTrajet";
        try{
            if(page == null){
                page = 1;
            }
            if(colonne == null){
                colonne = "id";
            }
            
            Integer nb_elem_show = Constant.NB_ELEM_PAGINATION_LIST_TRAJET;
            
           ServiceTrajet service = new ServiceTrajet();
           Object[] o = service.ListPaginationTrajet_tout(" 1=1 ",page, nb_elem_show,colonne);
            
            model.addAttribute("trajet_detail",(Trajet_detail[]) o[0]);
            model.addAttribute("page_current",page);
            model.addAttribute("nb_page_max",(int) o[1]);
            model.addAttribute("colonne",colonne);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
    
    @GetMapping("ListTarif_Get")
    public String ListTarfi(Model model,@RequestParam(required = false) Integer page,@RequestParam(required = false) String colonne){
        String res = "ListTarif";
        try{
            if(page == null){
                page = 1;
            }
            if(colonne == null){
                colonne = "id";
            }
            
            Integer nb_elem_show = Constant.NB_ELEM_PAGINATION_LIST_TARIF;
            
           ServiceTarif service = new ServiceTarif();
           Object[] o = service.ListPaginationTarif(" 1=1 ",page, nb_elem_show,colonne);
           
            model.addAttribute("tarif",(Tarif[]) o[0]);
            model.addAttribute("page_current",page);
            model.addAttribute("nb_page_max",(int) o[1]);
            model.addAttribute("colonne",colonne);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
    
    @GetMapping("AjoutTrajet_Gest")
    public String AjoutTrajet(Model model){
        try{
            Ville[] ville = new Ville().getInBase(null,null,"",null);
            model.addAttribute("ville",ville);
        }
        catch(Exception ex){
        }
        return "AjoutTrajet";
    }
    
    @GetMapping("ModifTrajet_Gest")
    public String ModifTrajet(Model model,String idTrajet){
        try{
            Trajet_detail tr = new Trajet_detail().getInBaseTout(idTrajet,null,null,"",null)[0];
            Ville[] ville = new Ville().getInBase(null,null,"",null);
            model.addAttribute("ville",ville);
            model.addAttribute("trajet",tr);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return "ModifTrajet";
    }
    
    @GetMapping("AjoutTarif_Gest")
    public String AjoutTarif(Model model){
         try{
            Trajet_detail[] td = new Trajet_detail().getInBaseTout(null,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            Tarif[] tr = new Tarif().getInBase(null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            model.addAttribute("trajet_detail",td);
            model.addAttribute("all_tarif",tr);
        }
        catch(Exception ex){
        }
        return "AjoutTarif";
    }
    
    @GetMapping("ModifTarif_Gest")
    public String ModifTarif(Model model,String idTarif){
        try{
            Trajet_detail[] td = new Trajet_detail().getInBaseTout(null,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            Tarif tr = new Tarif().getInBase(idTarif,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null)[0];
            
            model.addAttribute("trajet_detail",td);
            model.addAttribute("tarif",tr);
        }
        catch(Exception ex){
        }
        return "ModifTarif";
    }
    
     @GetMapping("AjoutVol_Gest")
    public String AjoutVol(Model model){
          try{
            Trajet_detail[] td = new Trajet_detail().getInBase(null,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            Avion[] av = new Avion().getInBase(null,null,"",null);
            model.addAttribute("trajet_detail",td);
            model.addAttribute("avion",av);
        }
        catch(Exception ex){
        }
        return "AjoutVol";
    }
    
    @GetMapping("ModifVol_Gest")
    public String ModifVol(Model model,String idVol){
        try{
            Trajet_detail[] td = new Trajet_detail().getInBase(null,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            Avion[] av = new Avion().getInBase(null,null,"",null);
            Vol vl = new Vol().getInBase(idVol,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null)[0];
            
            model.addAttribute("vol",vl);
            model.addAttribute("trajet_detail",td);
            model.addAttribute("avion",av);
        }
        catch(Exception ex){
        }
        return "ModifVol";
    }
    
    @GetMapping("AchatBillet_Gest")
    public String AchatBilletGest(Model model,String idVol){
        String res = "AchatBillet";
        try{
            Vol vl = new Vol().getInBase(idVol,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null)[0];
            model.addAttribute("vol",vl);         
        }
        catch(Exception ex){
        }
        return res;
    }
    
    @GetMapping("ListBillet_Gest")
    public String ListBillet(Model model,String idVol){
        String res = "ListBillet";
        try{
            Vol vl = null;
            Vol[] v = new Vol().getInBase(idVol,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            if(v.length <= 0){
                throw new Exception("Vol introuvable");
            }
            else{
                vl = v[0];
            }
            Billet_detail[] billet = vl.getBillet_details(null);
            model.addAttribute("billet",billet);       
            model.addAttribute("vol",vl);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
    
      @GetMapping("Remboursement_Gest")
    public String Remboursement(Model model,String idBillet,String idVol){
        String res = "Remboursement";
        try{
            Billet[] b = new Billet().getInBase(idBillet,null,"",null);
           if(b.length <=0 ){
                throw new Exception("Billet introuvable.");
           }
           if(b[0].getRemboursable() != 1){
                throw new Exception("Billet non remboursable.");
           }
            ConditionTarif ct = new ConditionTarif().getPenalite(null);
            double montant_payer = b[0].getMontant()* ct.getPourcentage()/100;
            model.addAttribute("billet",b[0]);
            model.addAttribute("penalite",ct.getPourcentage());
            model.addAttribute("idVol",idVol);
            model.addAttribute("montant_payer",montant_payer);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
    
    @GetMapping("Chiffre_Affaire")
    public String ChiffreAffaire(Model model,String idVol){
        String res = "Chiffre_Affaire";
        try{
           Vol_detail[] vd = new Vol_detail().getInBase(idVol,null,null,"",null);
           if(vd.length <=0 ){
               throw new Exception("Vol introuvable.");
           }
           double penalite = new ConditionTarif().getPenalite(null).getPourcentage();
           
           model.addAttribute("penalite",penalite);
           model.addAttribute("vol_detail",vd[0]);
           model.addAttribute("billet",vd[0].getBillet_details_With_Rembourse(null));
           model.addAttribute("chiffre_affaire",vd[0].CalculeChiffreAffaire(null));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
    
    @GetMapping("ModifBillet_Gest")
    public String ModifBilletGest(Model model,String idBillet,@RequestParam(required = false) Integer page,@RequestParam(required = false) String colonne){
        String res = "ModifBillet";
        try{
            if(page == null){
                page = 1;
            }    
            int   nb_elem_show = Constant.NB_ELEM_PAGINATION_LIST_MODIF_BILLET;
            if(colonne == null){
                colonne = "num";
            }
            Billet[] b = new Billet().getInBase(idBillet,null,"", null);
            if(b.length <= 0){
                throw new Exception("Billet introuvable.");
            }
            Vol_detail vl = b[0].getVol_detail(null);
           ServiceVol service = new ServiceVol();
            Object[] o = service.ListPaginationVol(" idTrajet = '"+vl.getIdTrajet()+"'",page,nb_elem_show,colonne);
            
            
          model.addAttribute("billet",b[0]);
           model.addAttribute("vol_detail",(Vol_detail[]) o[0]);
          model.addAttribute("page_current",page);
          model.addAttribute("nb_page_max",(int) o[1]);
          model.addAttribute("colonne",colonne);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
    
    @GetMapping("Validation_vol_Gest")
    public String Validation_vol(Model model,String idVol){
        String res = "Validation_vol";
        try{
            Vol vl = null;
            Vol[] v = new Vol().getInBase(idVol,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            if(v.length <= 0){
                throw new Exception("Vol introuvable");
            }
            else{
                vl = v[0];
            }
            Billet_detail[] billet = vl.getBillet_details(null);
            model.addAttribute("billet",billet);       
            model.addAttribute("vol",vl);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
    
    @GetMapping("ImprimerBillet")
    public void PDF(HttpServletResponse res,@RequestParam String idBillet){
        try{
            Billet[] b = new Billet().getInBase(idBillet,null, "", null);
            Billet bi = b[0];
            Vol_detail vl = bi.getVol_detail(null);
            //DATA
           String[] colonneVol_Verif = {"Num","Origin","Destination","datedepart","Horaire","avion"};
            String[] colonneVol_Affiche = {"Num","Origin","Destination","Date de départ","Horaire","Avion"};
            
            String[] colonneBillet_Verif = {"nom_personne","age","modifiable","remdoursable","montant"};
            String[] colonneBillet_Affiche = {"Nom Client","Age","Modifiable","Remdoursable","Montant (Ar)"};
             String[] colonneBillet_V_number = {"age","montant"};
            
            String[] colonneBillet_D_Verif = {"description","prix"};
            String[] colonneBillet_D_Affiche = {"Description","% ou Prix (Ar) "};
            String[] colonneBillet_D_V_number= {"prix"};
            
            //reduction
             double montant = 0;
            double  pourC_M = 0;
            double pourC_R = 0;
             double reduction =100;
            Reduction[] red = new Reduction().getInBase(null,null," age_max > '"+bi.getAge()+"' and age_min <= '"+bi.getAge()+"' ", null);
            if(red.length >= 0){
                   reduction = red[0].getPourcentage();
            }
        
            ConditionTarif modifiable = new ConditionTarif().getModifiable(null);
            ConditionTarif remboursable = new ConditionTarif().getRemboursable(null);
                if(modifiable.getDescription().equals("modifiable")){
                    pourC_M = modifiable.getPourcentage();
                    
                }
                if(remboursable.getDescription().equals("remboursable")){
                    pourC_R = remboursable.getPourcentage();
                }
            
            //condition tarif
            double pourcebtage_Ct = 0;
            if(bi.getModifiable() == 0){
                pourcebtage_Ct += pourC_M;
            }
            if(bi.getRemboursable() == 0){
                pourcebtage_Ct += pourC_R;
            }
            montant = vl.getPrix()*(reduction/100);
            double montantOption = montant;
            montant = montant - (montant*(pourcebtage_Ct/100));
            double montantVrai = montant;
            
            Vol_Affiche_PDF[] vd = new Vol_Affiche_PDF[1];
            vd[0] = new Vol_Affiche_PDF(vl.getNum(),vl.getOrigin(),vl.getDestination(),vl.getDateDep().toString(),vl.getHoraire().toString(),vl.getNom_avion());
            
            Billet_Affiche_PDF[] BA = new Billet_Affiche_PDF[1];
            String m = "";
            String r = "";
            if(bi.getModifiable() == 1){ m = "OUI"; }else{ m = "NON"; }
            if(bi.getRemboursable() == 1){ r = "OUI"; }else{ r = "NON"; }
            BA[0] = new Billet_Affiche_PDF(bi.getNomClient(),bi.getAge()+"",bi.getId(),m,r,bi.getMontant()+"");
            
            Detail_Billet_PDF[] DBP = new Detail_Billet_PDF[6];
            DBP[0] = new Detail_Billet_PDF("Pourcentage si modifiable",pourC_M+"%");
            DBP[1] = new Detail_Billet_PDF("Pourcentage si remboursable",pourC_R+"%");
            DBP[2] = new Detail_Billet_PDF("----------------------------------------------------------------","----------------------------------------------------------------");
            DBP[3] = new Detail_Billet_PDF("Prix de base",vl.getPrix()+"");
            DBP[4] = new Detail_Billet_PDF("Pourcentage option par age:","("+reduction+"%) "+montantOption);
            DBP[5] = new Detail_Billet_PDF("Apres condition tarifaire",montantVrai+"");
            
            
            ExportPDF exp = new ExportPDF("Evaluation 2","Evaluation 2","Tahiana","Tahiana");
            exp.exportHTTP(res);
            exp.addTitle("MadaAirlines",null);
            exp.addTitle2("YOUR TICKET -ITINERARY                 ",null);
            exp.addTable(colonneVol_Verif,colonneVol_Affiche,null,vd);
            exp.addTable(colonneBillet_Verif,colonneBillet_Affiche,colonneBillet_V_number,BA);
            exp.addTable(colonneBillet_D_Verif,colonneBillet_D_Affiche,colonneBillet_D_V_number,DBP);
            exp.addParagraph("MONTANT TOTAL                                                                                                      "+bi.getMontant()+" Ar","left");
            exp.getDocument().close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    @GetMapping("RechercheVol_Gest")
    public String RechecheVol(Model model){
        try{
            Trajet_detail[] trajet_detail = new Trajet_detail().getInBase(null,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            Avion[] avion = new Avion().getInBase(null, null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            
            model.addAttribute("trajet_detail",trajet_detail);
            model.addAttribute("avion",avion);
            model.addAttribute("vol_detail",new Vol_detail[0]);
            model.addAttribute("nb_page",1);
           model.addAttribute("page_current",1);
           model.addAttribute("colonne","num");

           //param
            model.addAttribute("date1","");
            model.addAttribute("date2","");
            model.addAttribute("horaire","");
            model.addAttribute("idAvion","Tout");
            model.addAttribute("idTrajet","Tout");
        }
        catch(Exception e){
            
        }
        return "RechercheVol";
    }
    
    @PostMapping("AjoutTrajet")
    public String AjoutTrajet(RedirectAttributes model,@ModelAttribute Trajet trajet){
        String res = "AjoutTrajet_Gest";
        try{
            ServiceTrajet service = new ServiceTrajet();
            service.Ajout(trajet);
        }
        catch(Exception ex){
            model.addFlashAttribute("trajet",trajet);
            model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
    @PostMapping("ModifTrajet")
    public String ModifTrajet(RedirectAttributes model,@ModelAttribute Trajet trajet){
        String res = "ModifTrajet_Gest?idTrajet="+trajet.getId();
        try{
            ServiceTrajet service = new ServiceTrajet();
            service.Modif(trajet);
        }
        catch(Exception ex){
            model.addFlashAttribute("trajet",trajet);
            model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
    @GetMapping("SuppreTrajet")
    public String SuppreTrajet(HashMap<String,Object> model,String idTrajet){
        String res = "ListTrajet_Get";
        try{
            ServiceTrajet service = new ServiceTrajet();
            service.Suppre(idTrajet);
        }
        catch(Exception ex){
            model.put("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
    @PostMapping("AjoutTarif")
    public String AjoutTarif(RedirectAttributes model,@ModelAttribute Tarif tarif){
        String res = "AjoutTarif_Gest";
        try{
            ServiceTarif service = new ServiceTarif();
            service.Ajout(tarif);
        }
        catch(Exception ex){
           model.addFlashAttribute("tarif",tarif);
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
    @PostMapping("ModifTarif")
    public String ModifTarif(RedirectAttributes model,@ModelAttribute Tarif tarif){
        String res = "ModifTarif_Gest?idTarif="+tarif.getId();
        try{
            ServiceTarif service = new ServiceTarif();
            service.Modif(tarif);
        }
        catch(Exception ex){
            model.addFlashAttribute("tarif",tarif);
            model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
    @GetMapping("SuppreTarif")
    public String SuppreTarif(Model model,String idTarif){
        String res = "ListTarif_Get";
        try{
            ServiceTarif service = new ServiceTarif();
            service.Suppre(idTarif);
        }
        catch(Exception ex){
            model.addAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
     @PostMapping("AjoutVol")
    public String AjoutVol(RedirectAttributes model,@ModelAttribute Vol vol){
        String res = "AjoutVol_Gest";
        //Vol vol = null;
        try{
            ServiceVol service = new ServiceVol();
            service.Ajout(vol);
        }
        catch(Exception ex){
             model.addFlashAttribute("vol",vol);
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
     @PostMapping("ModifVol")
    public String ModifVol(RedirectAttributes model,@ModelAttribute Vol vol){
        String res = "ModifVol_Gest";
        //Vol vol = null;
        try{
            ServiceVol service = new ServiceVol();
            service.Modif(vol);
        }
        catch(Exception ex){
           model.addFlashAttribute("vol",vol);
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        model.addFlashAttribute("idVol",vol.getId());
        return "redirect:/"+res+"?idVol="+vol.getId();
    }
    
    @GetMapping("SuppreVol")
    public String SuppreVol(RedirectAttributes model,@RequestParam String idVol){
        String res = "RechercheVol";
        //Vol vol = null;
        try{
            ServiceVol service = new ServiceVol();
            service.Suppre(idVol);
        }
        catch(Exception ex){
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return res;
    }
    
    @GetMapping("RechercheVol")
    public String RechercheVol(Model model,@RequestParam String date1,@RequestParam String date2,@RequestParam String horaire,@RequestParam String idTrajet,@RequestParam String idAvion,@RequestParam String page,@RequestParam String colonne){
        String res = "RechercheVol";
        //Vol vol = null;
        try{
             Trajet_detail[] trajet_detail = new Trajet_detail().getInBase(null,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);
            Avion[] avion = new Avion().getInBase(null, null," etat != '"+Constant.ETAT_SUPPRIMER+"'",null);          
            model.addAttribute("trajet_detail",trajet_detail);
            model.addAttribute("avion",avion);
            
            int nb_elem_show = Constant.NB_ELEM_PAGINATION_RECHERCHE;
            ServiceVol service = new ServiceVol();
           Object[] o = service.Recherche(date1, date2, horaire, idTrajet, idAvion,Integer.parseInt(page),nb_elem_show,colonne);
            Vol_detail[] vol_detail = (Vol_detail[]) o[0];
             Integer nb_data = (int) o[1];
            int nb_page_max = nb_data/nb_elem_show;
              if((nb_data%nb_elem_show) > 0){
                nb_page_max++;
            }
           model.addAttribute("vol_detail",vol_detail);
           model.addAttribute("nb_page",nb_page_max);
           model.addAttribute("page_current",Integer.parseInt(page));
           model.addAttribute("colonne",colonne);
           
           //param
           model.addAttribute("date1",date1);
           model.addAttribute("date2",date2);
           model.addAttribute("horaire",horaire);
           model.addAttribute("idAvion",idAvion);
           model.addAttribute("idTrajet",idTrajet);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            model.addAttribute("Erreur",ex.getMessage());
        }
        return res;
    }
    
     @PostMapping("AchatBillet")
    public String AchatBillet(RedirectAttributes model,@RequestParam String nom,@RequestParam Date date,@RequestParam(required = false) String modifiable,@RequestParam(required = false) String remboursable,@RequestParam() String idVol){
        String res = "AchatBillet";
        //Vol vol = null;
        try{           
           int modfi = 1;
           int remb = 1;
          
           if(modifiable == null){
               modfi = 0;
           }
         
           if(remboursable == null){
               remb = 0;
           }          
           Vol[] v = new Vol().getInBase(idVol,null,null,"", null);
           
           if(v.length <= 0){
               throw new Exception("Vol introuvable.");
           }
           Date dateVol = v[0].getDateDep();
            LocalDate dv = dateVol.toLocalDate();
            LocalDate dN = date.toLocalDate();
            int age = Period.between( dN,dv).getYears();
            Billet billet = new Billet("",nom,age,idVol,modfi,remb,0.0,null,0);    
            ServiceBillet service = new ServiceBillet();
            service.Achat(billet);
        }
        catch(Exception ex){
            ex.printStackTrace();
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/AchatBillet_Gest?idVol="+idVol;
    }
    
    @PostMapping("ValiderVol")
    public String ValidationVol(RedirectAttributes model,@RequestParam(required = false) String[] present,@RequestParam String idVol){
        String res = "Validation_vol_Gest";
        try{           
          if(present == null){
              present = new String[0];
          }       
          ServiceVol service = new ServiceVol();
          service.Valider(idVol, present);
        }
        catch(Exception ex){
            ex.printStackTrace();
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res+"?idVol="+idVol;
    }
    
     @PostMapping("RembourserBillet")
    public String RembourserBillet(RedirectAttributes model,@RequestParam String idBillet,@RequestParam String idVol,String date){
        String res = "ListBillet_Gest?idVol="+idVol;
        try{           
            ServiceBillet service = new ServiceBillet();
            service.Rembourser(idBillet,date);
        }
        catch(Exception ex){
           res="Remboursement_Gest?idBillet="+idBillet+"&idVol="+idVol;
           
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
    @GetMapping("ModifBillet")
    public String ModifBillet(RedirectAttributes model,@RequestParam String idBillet,@RequestParam String idVol){
        String res = "RechercheVol_Gest";
        try{           
            Billet[] b = new Billet().getInBase(idBillet,null,"",null);
            if(b.length <= 0){
                throw new Exception("Billet introuvable.");
            }
            if(b[0].getEtat() == Constant.ETAT_VALIDER){
                throw new Exception("Billet déja validé.");
            }else{
               b[0].Modifier(idVol,null);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
           model.addFlashAttribute("Erreur",ex.getMessage());
        }
        return "redirect:/"+res;
    }
    
}
