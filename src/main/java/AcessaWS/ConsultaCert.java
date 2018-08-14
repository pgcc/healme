/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcessaWS;

import Model.PlatformModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.thoughtworks.xstream.XStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.faces.application.FacesMessage;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

/**
 *
 * @author icarv
 */
@ManagedBean
public class ConsultaCert implements Serializable {

    //private String servidor = "192.168.1.104";
    //private String servidor = "localhost";
    private String servidor = "200.131.219.85";
    private String endpoit = "http://" + servidor + ":8080" + recurso;
    static final String recurso = "/wshealme/rest/WSSecoRestful/";
    private String namePlatform;
    protected PlatformModel model;
    private PlatformModel modelSave = new PlatformModel();
    private PlatformModel modelSOrigin = new PlatformModel();
    private List<PlatformModel> Lista;
    private List<String> platforms = new ArrayList<String>();
    private String[] namePlatforms;
    
    private String mensage;
    private String titulo;
    private String icone;

    public String getMensage() {
        return mensage;
    }

    public void setMensage(String mensage) {
        this.mensage = mensage;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
    
    
    
    
    

    private boolean newRegister = true;
    private boolean save;
    private boolean edit;
    private boolean editReg;
    private boolean delete;
    private boolean cancel;

    public boolean isEditReg() {
        return editReg;
    }

    public void setEditReg(boolean editReg) {
        this.editReg = editReg;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isNewRegister() {
        return newRegister;
    }

    public void setNewRegister(boolean newRegister) {
        this.newRegister = newRegister;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public PlatformModel getModel() {
        return model;
    }

    public void setModel(PlatformModel model) {
        this.model = model;
    }

    public PlatformModel getModelSave() {
        return modelSave;
    }

    public void setModelSave(PlatformModel modelSave) {
        this.modelSave = modelSave;
    }

    

    public List<PlatformModel> getLista() {
        return Lista;
    }

    public void setLista(List<PlatformModel> Lista) {
        this.Lista = Lista;
    }

    public String getNamePlatform() {
        return this.namePlatform;
    }

    public void setNamePlatform(String namePlatform) {
        this.namePlatform = namePlatform;
    }

    public void setPlatforms(String[] platforms) {
        this.namePlatforms = platforms;
        this.namePlatforms[0] = "Teste";
        this.namePlatform = this.namePlatforms[0];
    }

    /*@PostConstruct
    public void init() {
        this.editReg = true;
        this.newRegister = false;
        this.save = true;
        this.edit = true;
        this.cancel = true;
        this.delete = true;
    }*/
    public void carregaModel() throws ParserConfigurationException, SAXException {
        this.getData();
        for (int i = 0; i < this.Lista.size(); i++) {
            if (this.Lista.get(i).getName().equals(this.namePlatform)) {
                this.model = this.Lista.get(i);
                break;
            }
        }
    }

    public void carregaModelSave() throws ParserConfigurationException, SAXException, MalformedURLException, IOException {
        URL url = new URL(endpoit + "getplatform/" + this.namePlatform);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output = "", captura;
        captura = br.readLine();
        while (captura != null) {
            output += captura;
            output += "\n";
            captura = br.readLine();
        }

        conn.disconnect();
        this.modelSave = new PlatformModel();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(output)));

            XStream xstream = new XStream();
            xstream.alias("platformModel", PlatformModel.class);

            this.modelSave = (PlatformModel) xstream.fromXML(output);
            this.modelSOrigin = this.modelSave;
            this.newRegister = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    

    public void carregaModelSOrigin() throws ParserConfigurationException, SAXException, MalformedURLException, IOException {
        URL url = new URL(endpoit + "getplatform/" + this.namePlatform);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output = "", captura;
        captura = br.readLine();
        while (captura != null) {
            output += captura;
            output += "\n";
            captura = br.readLine();
        }

        conn.disconnect();
        this.modelSOrigin = new PlatformModel();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(output)));

            XStream xstream = new XStream();
            xstream.alias("platformModel", PlatformModel.class);

            this.modelSOrigin = (PlatformModel) xstream.fromXML(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editModel() throws UnsupportedEncodingException, IOException, ParserConfigurationException, SAXException {

        carregaModelSOrigin();
        
        if(this.modelSave.getName() != null && !this.modelSave.getName().equals("")){
            this.modelSave.setActiveMembersPar(this.modelSOrigin.getActiveMembersPar());
            this.modelSave.setNumberOfCommunitsPar(this.modelSOrigin.getNumberOfCommunitsPar());
            this.modelSave.setTotalEfforPar(this.modelSOrigin.getTotalEfforPar());
            this.modelSave.setNumberOfEventParticipantsPar(this.modelSOrigin.getNumberOfEventParticipantsPar());
            this.modelSave.setNumberOfJobAdvertisementsPar(this.modelSOrigin.getNumberOfJobAdvertisementsPar());
            this.modelSave.setNumberOfDownloadsPar(this.modelSOrigin.getNumberOfDownloadsPar());
            this.modelSave.setNumberOfReadersPar(this.modelSOrigin.getNumberOfReadersPar());
            this.modelSave.setNumberOfScientificPublicationPar(this.modelSOrigin.getNumberOfScientificPublicationPar());
            this.modelSave.setNumberOfSocialMediaHitsPar(this.modelSOrigin.getNumberOfSocialMediaHitsPar());
            this.modelSave.setNumberOfWebPageRequestsPar(this.modelSOrigin.getNumberOfWebPageRequestsPar());
            this.modelSave.setNumberOfDevelopersPar(this.modelSOrigin.getNumberOfDevelopersPar());
            this.modelSave.setNumberOfUsersGroupsPar(this.modelSOrigin.getNumberOfUsersGroupsPar());
            this.modelSave.setNumberOfProgrammingLanguagesSupportedPar(this.modelSOrigin.getNumberOfProgrammingLanguagesSupportedPar());
            this.modelSave.setExistPlanForCollapsePar(this.modelSOrigin.getExistPlanForCollapsePar());
            this.modelSave.setNumberOfProjectsAddedPar(this.modelSOrigin.getNumberOfProjectsAddedPar());
            this.modelSave.setNumberOfEventsPar(this.modelSOrigin.getNumberOfEventsPar());
            this.modelSave.setNumberOfArtifactsPar(this.modelSOrigin.getNumberOfArtifactsPar());
            this.modelSave.setNumberOfTransmittedMessagesPar(this.modelSOrigin.getNumberOfTransmittedMessagesPar());
            this.modelSave.setBugFixTimePar(this.modelSOrigin.getBugFixTimePar());
            this.modelSave.setNumberOfPartnersAddedPar(this.modelSOrigin.getNumberOfPartnersAddedPar());
            this.modelSave.setNumberOfUsersPar(this.modelSOrigin.getNumberOfUsersPar());
            this.modelSave.setAverageTimeUsePar(this.modelSOrigin.getAverageTimeUsePar());
            this.modelSave.setNumberOfNodesConnectionsPar(this.modelSOrigin.getNumberOfNodesConnectionsPar());
            this.modelSave.setConnectivityCapacityPar(this.modelSOrigin.getConnectivityCapacityPar());
            this.modelSave.setRatioConnectionsCapacityPar(this.modelSOrigin.getRatioConnectionsCapacityPar());
            this.modelSave.setNodesCentralityPar(this.modelSOrigin.getNodesCentralityPar());
            this.modelSave.setNumberOfExternalPartnersPar(this.modelSOrigin.getNumberOfExternalPartnersPar());
            this.modelSave.setNumberOfProductTypesPar(this.modelSOrigin.getNumberOfProductTypesPar());
            this.modelSave.setGreaterCollaborationPar(this.modelSOrigin.getGreaterCollaborationPar());
            this.modelSave.setNumberOfActiveProjectsPar(this.modelSOrigin.getNumberOfActiveProjectsPar());
            this.modelSave.setNumberOfPartnersPar(this.modelSOrigin.getNumberOfPartnersPar());
            this.modelSave.setNumberOfCommercialSponsorsPar(this.modelSOrigin.getNumberOfCommercialSponsorsPar());
            this.modelSave.setTotalContributionValuePar(this.modelSOrigin.getTotalContributionValuePar());
            this.modelSave.setNumberOfActiveContributorsPar(this.modelSOrigin.getNumberOfActiveContributorsPar());
            this.modelSave.setNumberOfFrequentUsersPar(this.modelSOrigin.getNumberOfFrequentUsersPar());
            this.modelSave.setHaveDocumentationPar(this.modelSOrigin.getHaveDocumentationPar());
            this.modelSave.setNumberOfContributorsTypesPar(this.modelSOrigin.getNumberOfContributorsTypesPar());
            this.modelSave.setNumberOfTypesAppProjectsPar(this.modelSOrigin.getNumberOfTypesAppProjectsPar());
            this.modelSave.setSupportNaturalLanguagesPar(this.modelSOrigin.getSupportNaturalLanguagesPar());
            this.modelSave.setNumberOfTypesTechSupportedPar(this.modelSOrigin.getNumberOfTypesTechSupportedPar());
            this.modelSave.setNumberOfTypesDevTechSupportedPar(this.modelSOrigin.getNumberOfTypesDevTechSupportedPar());
            this.modelSave.setNumberOfCountriesPar(this.modelSOrigin.getNumberOfCountriesPar());
            this.modelSave.setSemanticClosenessAvgPar(this.modelSOrigin.getSemanticClosenessAvgPar());
            this.modelSave.setNumberOfNodeTypesPar(this.modelSOrigin.getNumberOfNodeTypesPar());
            this.modelSave.setTimeWorkTogetherPar(this.modelSOrigin.getTimeWorkTogetherPar());
            this.modelSave.setNumberOfNewMembersPar(this.modelSOrigin.getNumberOfNewMembersPar());
            this.modelSave.setDeveloperCommitsPar(this.modelSOrigin.getDeveloperCommitsPar());

            Client client;
            WebTarget webTarget;

            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(endpoit);
            String resposta;
            try {
                
                if(this.modelSave.getName().equals(this.modelSOrigin.getName())){
                    resposta = webTarget.path("edit").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(this.modelSave, javax.ws.rs.core.MediaType.APPLICATION_XML), String.class);
                }else{
                    resposta = webTarget.path("create").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(this.modelSave, javax.ws.rs.core.MediaType.APPLICATION_XML), String.class);
                }
                
               if(resposta.equals("ok")){
                   this.titulo = "Success";
                   this.mensage = "Sucess while save platform";
                }else{
                   this.titulo = "Error";
                   this.mensage = "Error while save platform: " + resposta;
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.titulo = "Error";
                this.mensage = "Error while save platform: " + e.getMessage();
            }finally{
                RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
            }
        }else{
            this.titulo = "Error";
            this.mensage = "Error while save platform: Set a name to Platform";
            RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
        }
        
    }

    public void editParamModel() throws UnsupportedEncodingException, IOException, ParserConfigurationException, SAXException {

        
        carregaModelSOrigin();

        this.modelSave.setName(this.modelSOrigin.getName());
        this.modelSave.setActiveMembers(this.modelSOrigin.getActiveMembers());
        this.modelSave.setNumberOfCommunits(this.modelSOrigin.getNumberOfCommunits());
        this.modelSave.setTotalEffor(this.modelSOrigin.getTotalEffor());
        this.modelSave.setNumberOfEventParticipants(this.modelSOrigin.getNumberOfEventParticipants());
        this.modelSave.setNumberOfJobAdvertisements(this.modelSOrigin.getNumberOfJobAdvertisements());
        this.modelSave.setNumberOfDownloads(this.modelSOrigin.getNumberOfDownloads());
        this.modelSave.setNumberOfReaders(this.modelSOrigin.getNumberOfReaders());
        this.modelSave.setNumberOfScientificPublication(this.modelSOrigin.getNumberOfScientificPublication());
        this.modelSave.setNumberOfSocialMediaHits(this.modelSOrigin.getNumberOfSocialMediaHits());
        this.modelSave.setNumberOfWebPageRequests(this.modelSOrigin.getNumberOfWebPageRequests());
        this.modelSave.setNumberOfDevelopers(this.modelSOrigin.getNumberOfDevelopers());
        this.modelSave.setNumberOfUsersGroups(this.modelSOrigin.getNumberOfUsersGroups());
        this.modelSave.setNumberOfProgrammingLanguagesSupported(this.modelSOrigin.getNumberOfProgrammingLanguagesSupported());
        this.modelSave.setExistPlanForCollapse(this.modelSOrigin.getExistPlanForCollapse());
        this.modelSave.setNumberOfProjectsAdded(this.modelSOrigin.getNumberOfProjectsAdded());
        this.modelSave.setNumberOfEvents(this.modelSOrigin.getNumberOfEvents());
        this.modelSave.setNumberOfArtifacts(this.modelSOrigin.getNumberOfArtifacts());
        this.modelSave.setNumberOfTransmittedMessages(this.modelSOrigin.getNumberOfTransmittedMessages());
        this.modelSave.setBugFixTime(this.modelSOrigin.getBugFixTime());
        this.modelSave.setNumberOfPartnersAdded(this.modelSOrigin.getNumberOfPartnersAdded());
        this.modelSave.setNumberOfUsers(this.modelSOrigin.getNumberOfUsers());
        this.modelSave.setAverageTimeUse(this.modelSOrigin.getAverageTimeUse());
        this.modelSave.setNumberOfNodesConnections(this.modelSOrigin.getNumberOfNodesConnections());
        this.modelSave.setConnectivityCapacity(this.modelSOrigin.getConnectivityCapacity());
        this.modelSave.setRatioConnectionsCapacity(this.modelSOrigin.getRatioConnectionsCapacity());
        this.modelSave.setNodesCentrality(this.modelSOrigin.getNodesCentrality());
        this.modelSave.setNumberOfExternalPartners(this.modelSOrigin.getNumberOfExternalPartners());
        this.modelSave.setNumberOfProductTypes(this.modelSOrigin.getNumberOfProductTypes());
        this.modelSave.setGreaterCollaboration(this.modelSOrigin.getGreaterCollaboration());
        this.modelSave.setNumberOfActiveProjects(this.modelSOrigin.getNumberOfActiveProjects());
        this.modelSave.setNumberOfPartners(this.modelSOrigin.getNumberOfPartners());
        this.modelSave.setNumberOfCommercialSponsors(this.modelSOrigin.getNumberOfCommercialSponsors());
        this.modelSave.setTotalContributionValue(this.modelSOrigin.getTotalContributionValue());
        this.modelSave.setNumberOfActiveContributors(this.modelSOrigin.getNumberOfActiveContributors());
        this.modelSave.setNumberOfFrequentUsers(this.modelSOrigin.getNumberOfFrequentUsers());
        this.modelSave.setHaveDocumentation(this.modelSOrigin.getHaveDocumentation());
        this.modelSave.setNumberOfContributorsTypes(this.modelSOrigin.getNumberOfContributorsTypes());
        this.modelSave.setNumberOfTypesAppProjects(this.modelSOrigin.getNumberOfTypesAppProjects());
        this.modelSave.setSupportNaturalLanguages(this.modelSOrigin.getSupportNaturalLanguages());
        this.modelSave.setNumberOfTypesTechSupported(this.modelSOrigin.getNumberOfTypesTechSupported());
        this.modelSave.setNumberOfTypesDevTechSupported(this.modelSOrigin.getNumberOfTypesDevTechSupported());
        this.modelSave.setNumberOfCountries(this.modelSOrigin.getNumberOfCountries());
        this.modelSave.setSemanticClosenessAvg(this.modelSOrigin.getSemanticClosenessAvg());
        this.modelSave.setNumberOfNodeTypes(this.modelSOrigin.getNumberOfNodeTypes());
        this.modelSave.setTimeWorkTogether(this.modelSOrigin.getTimeWorkTogether());
        this.modelSave.setNumberOfNewMembers(this.modelSOrigin.getNumberOfNewMembers());
        this.modelSave.setDeveloperCommits(this.modelSOrigin.getDeveloperCommits());

        Client client;
        WebTarget webTarget;

        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(endpoit);
        String resposta;
        try {
            resposta = webTarget.path("edit").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(this.modelSave, javax.ws.rs.core.MediaType.APPLICATION_XML), String.class);
            if(resposta.equals("ok")){
               this.titulo = "Success";
               this.mensage = "Sucess while save platform";
            }else{
               this.titulo = "Error";
               this.mensage = "Error while save platform: " + resposta;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.titulo = "Error";
            this.mensage = "Error while save platform: " + e.getMessage();
        }finally{
            RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
        }
    }

    public String[] getPlatforms() throws ParserConfigurationException, SAXException {
        try {
            URL url = new URL(endpoit + "getplatforms");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = "", captura;
            captura = br.readLine();
            while (captura != null) {
                output += captura;
                output += "\n";
                captura = br.readLine();
            }

            conn.disconnect();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(output)));
                NodeList nodes = document.getElementsByTagName("name");
                this.platforms = new ArrayList<String>();
                for (int i = 0; i < nodes.getLength(); i++) {
                    this.platforms.add(nodes.item(i).getChildNodes().item(0).getNodeValue());
                }
                String[] a = new String[this.platforms.size()];
                return this.platforms.toArray((String[]) a);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();
            //return e.getMessage();

        } catch (IOException e) {

            e.printStackTrace();
            //return e.getMessage();

        }
        return null;
    }

    public void getData() throws ParserConfigurationException, SAXException {
        try {
            this.Lista = new ArrayList<PlatformModel>();
            URL url = new URL(endpoit + "getdata/" + this.namePlatform);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = "", captura;
            captura = br.readLine();
            while (captura != null) {
                output += captura;
                output += "\n";
                captura = br.readLine();
            }

            conn.disconnect();
            this.model = new PlatformModel();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(output)));

                XStream xstream = new XStream();
                xstream.alias("platformModels", List.class);
                xstream.alias("platformModel", PlatformModel.class);

                List<PlatformModel> ps = (List<PlatformModel>) xstream.fromXML(output);
                this.Lista = ps;
                Iterator<PlatformModel> i = ps.iterator();
                while (i.hasNext()) {
                    Object o = i.next();
                    if (!(o instanceof PlatformModel)) {// just in case some XML error
                        System.out.println("class: " + o.getClass().getCanonicalName());
                        continue;
                    }
                    PlatformModel p = (PlatformModel) o;
                    this.Lista.add(p);
                }

                //return this.Lista;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        //return null;
    }

    public double retornaHet() {
        double media = retornaNumOfCountries() + retornaSemanticC() + retornaNumOfNodesType();
        return media / 3;
    }

    public double retornaNumOfCountries() {
        double retorno = (this.model.getNumberOfCountries() * 100) / this.model.getNumberOfCountriesPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaSemanticC() {
        double retorno = (this.model.getSemanticClosenessAvg() * 100) / this.model.getSemanticClosenessAvgPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumOfNodesType() {
        double retorno = (this.model.getNumberOfNodeTypes() * 100) / this.model.getNumberOfNodeTypesPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaRegAb() {
        double media = retornaTimeWorkTogether() + retornaNumberOfNewMembers();
        return media / 2;
    }

    public double retornaTimeWorkTogether() {
        double retorno = (this.model.getTimeWorkTogether() * 100) / this.model.getTimeWorkTogetherPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfNewMembers() {
        double retorno = (this.model.getNumberOfNewMembers() * 100) / this.model.getNumberOfNewMembersPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaEffBal() {
        double media = retornaDeveloperCommits() + retornaActiveMembers() + retornaNumberOfCommunits() + retornaTotalEffor();
        return media / 4;
    }

    public double retornaDeveloperCommits() {
        double retorno = (this.model.getDeveloperCommits() * 100) / this.model.getDeveloperCommitsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaActiveMembers() {
        double retorno = (this.model.getActiveMembers() * 100) / this.model.getActiveMembersPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfCommunits() {
        double retorno = (this.model.getNumberOfCommunits() * 100) / this.model.getNumberOfCommunitsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaTotalEffor() {
        double retorno = (this.model.getTotalEffor() * 100) / this.model.getTotalEfforPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaExBal() {
        return retornaNumberOfEventParticipants();
    }

    public double retornaNumberOfEventParticipants() {
        double retorno = (this.model.getNumberOfEventParticipants() * 100) / this.model.getNumberOfEventParticipantsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaVis() {
        double media = retornaNumberOfDownloads() + retornaNumberOfJobAdvertisements() + retornaNumberOfReaders() + retornaNumberOfScientificPublication() + retornaNumberOfSocialMediaHits() + retornaNumberOfWebPageRequests();
        return media / 6.0;
    }

    public double retornaNumberOfDownloads() {
        double retorno = (this.model.getNumberOfDownloads() * 100) / this.model.getNumberOfDownloadsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfJobAdvertisements() {
        double retorno = (this.model.getNumberOfJobAdvertisementsPar() * 100) / this.model.getNumberOfJobAdvertisementsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfReaders() {
        double retorno = (this.model.getNumberOfReaders() * 100) / this.model.getNumberOfReadersPar();
        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfScientificPublication() {
        double retorno = (this.model.getNumberOfScientificPublication() * 100) / this.model.getNumberOfScientificPublicationPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfSocialMediaHits() {
        double retorno = (this.model.getNumberOfSocialMediaHits() * 100) / this.model.getNumberOfSocialMediaHitsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfWebPageRequests() {
        double retorno = (this.model.getNumberOfWebPageRequests() * 100) / this.model.getNumberOfWebPageRequestsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaSust() {
        return (retornaHet() + retornaRegAb() + retornaEffBal() + retornaExBal() + retornaVis()) / 5.0;
    }

    public double retornaDiv() {
        double media = retornaNumberOfDevelopers() + retornaNumberOfUsersGroups() + retornaNumberOfProgrammingLanguages() + retornaPlanForCollapse();
        return media / 4.0;
    }

    public double retornaNumberOfDevelopers() {
        double retorno = (this.model.getNumberOfDevelopers() * 100) / this.model.getNumberOfDevelopersPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfUsersGroups() {
        double retorno = (this.model.getNumberOfUsersGroups() * 100) / this.model.getNumberOfUsersGroupsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfProgrammingLanguages() {
        double retorno = (this.model.getNumberOfProgrammingLanguagesSupported() * 100) / this.model.getNumberOfProgrammingLanguagesSupportedPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaPlanForCollapse() {

        double retorno = (this.model.getExistPlanForCollapse().equals(this.model.getExistPlanForCollapsePar()) ? 1 : 0) * 100.0;
        return retorno;

    }

    public double retornaProdut() {
        double media = retornaNumberOfProjectsAdded() + retornaNumberOfEvents() + retornaNumberOfArtifacts() + retornaNumberOfTransmittedMessages() + retornaBugFixTime() + retornaNumberOfPartnersAdded() + retornaNumberOfUsers() + retornaAverageTimeUse();
        return media / 8.0;
    }

    public double retornaNumberOfProjectsAdded() {
        double retorno = (this.model.getNumberOfProjectsAdded() * 100) / this.model.getNumberOfProjectsAddedPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfEvents() {
        double retorno = (this.model.getNumberOfEvents() * 100) / this.model.getNumberOfEventsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfArtifacts() {
        double retorno = (this.model.getNumberOfArtifacts() * 100) / this.model.getNumberOfArtifactsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfTransmittedMessages() {
        double retorno = (this.model.getNumberOfTransmittedMessages() * 100) / this.model.getNumberOfTransmittedMessagesPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaBugFixTime() {

        double retorno;
        
        if(this.model.getBugFixTime() <=  this.model.getBugFixTimePar()){
           retorno = 100; 
        }else if(this.model.getBugFixTime() >= (2 *  this.model.getBugFixTimePar())){
            retorno = 0;
        }else{
            double aux = (this.model.getBugFixTimePar() - this.model.getBugFixTime()) * (-1);
            retorno = (aux * 100)/this.model.getBugFixTimePar();
        }

        return retorno;
    }

    public double retornaNumberOfPartnersAdded() {
        double retorno = (this.model.getNumberOfPartnersAdded() * 100) / this.model.getNumberOfPartnersAddedPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfUsers() {
        double retorno = (this.model.getNumberOfUsers() * 100) / this.model.getNumberOfUsersPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaAverageTimeUse() {
        double retorno = (this.model.getAverageTimeUse() * 100) / this.model.getAverageTimeUsePar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaInterr() {
        double media = retornaNumberOfNodesConnections() + retornaConnectivityCapacity() + retornaRatioConnectionsCapacity() + retornaNodesCentrality() + retornaNumberOfExternalPartners();
        return media / 5.0;
    }

    public double retornaNumberOfNodesConnections() {
        double retorno = (this.model.getNumberOfNodesConnections() * 100) / this.model.getNumberOfNodesConnectionsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaConnectivityCapacity() {
        double retorno = (this.model.getConnectivityCapacity() * 100) / this.model.getConnectivityCapacityPar();

        if (retorno > 100) {
            retorno = 100;
        }
        return retorno;
    }

    public double retornaRatioConnectionsCapacity() {
        double retorno;
        if(this.model.getRatioConnectionsCapacity() <= this.model.getRatioConnectionsCapacityPar()){
           retorno = 100; 
        }else if(this.model.getRatioConnectionsCapacity() >= (2 * this.model.getRatioConnectionsCapacityPar())){
            retorno = 0;
        }else{
            double aux = ( this.model.getRatioConnectionsCapacityPar() - this.model.getRatioConnectionsCapacity()) * (-1);
            retorno = (aux * 100)/this.model.getRatioConnectionsCapacityPar();
        }

        return retorno;
    }

    public double retornaNodesCentrality() {
        double retorno = (this.model.getNodesCentrality() * 100) / this.model.getNodesCentralityPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfExternalPartners() {
        double retorno = (this.model.getNumberOfExternalPartners() * 100) / this.model.getNumberOfExternalPartnersPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaClust() {
        double media = retornaNumberOfProductTypes() + retornaGreaterCollaboration() + retornaNumberOfActiveProjects();
        return media / 3.0;
    }

    public double retornaNumberOfProductTypes() {
        double retorno = (this.model.getNumberOfProductTypes() * 100) / this.model.getNumberOfProductTypesPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaGreaterCollaboration() {
        double retorno = (this.model.getGreaterCollaboration() * 100) / this.model.getGreaterCollaborationPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfActiveProjects() {
        double retorno = (this.model.getNumberOfActiveProjects() * 100) / this.model.getNumberOfActiveProjectsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaFinCons() {
        double media = retornaNumberOfPartners() + retornaNumberOfCommercialSponsors() + retornaTotalContributionValue() + retornaNumberOfActiveContributors() + retornaNumberOfFrequentUsers();
        return media / 5.0;
    }

    public double retornaNumberOfPartners() {
        double retorno = (this.model.getNumberOfPartners() * 100) / this.model.getNumberOfPartnersPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfCommercialSponsors() {
        double retorno = (this.model.getNumberOfCommercialSponsors() * 100) / this.model.getNumberOfCommercialSponsorsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaTotalContributionValue() {
        double retorno = (this.model.getTotalContributionValue() * 100) / this.model.getTotalContributionValuePar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfActiveContributors() {
        double retorno = (this.model.getNumberOfActiveContributors() * 100) / this.model.getNumberOfActiveContributorsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfFrequentUsers() {
        double retorno = (this.model.getNumberOfFrequentUsers() * 100) / this.model.getNumberOfFrequentUsersPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaRobust() {
        return (retornaInterr() + retornaClust() + retornaFinCons()) / 3.0;
    }

    public double retornaNiCrea() {
        double media = retornaHaveDocumentation() + retornaNumberOfContributorsTypes() + retornaNumberOfTypesAppProjects() + retornaSupportNaturalLanguages() + retornaNumberOfTypesTechSupported() + retornaNumberOfTypesDevTechSupported();
        return media / 6.0;
    }

    public double retornaHaveDocumentation() {
        double retorno = (this.model.getHaveDocumentation().equals(this.model.getHaveDocumentationPar()) ? 1.0 : 0.0) * 100.0;
        return retorno;
    }

    public double retornaNumberOfContributorsTypes() {
        double retorno = (this.model.getNumberOfContributorsTypes() * 100) / this.model.getNumberOfContributorsTypesPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfTypesAppProjects() {
        double retorno = (this.model.getNumberOfTypesAppProjects() * 100) / this.model.getNumberOfTypesAppProjectsPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaSupportNaturalLanguages() {
        double retorno = (this.model.getSupportNaturalLanguages().equals(this.model.getSupportNaturalLanguagesPar()) ? 1.0 : 0.0) * 100.0;

        return retorno;
    }

    public double retornaNumberOfTypesTechSupported() {
        double retorno = (this.model.getNumberOfTypesTechSupported() * 100) / this.model.getNumberOfTypesTechSupportedPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaNumberOfTypesDevTechSupported() {
        double retorno = (this.model.getNumberOfTypesDevTechSupported() * 100) / this.model.getNumberOfTypesDevTechSupportedPar();

        if (retorno > 100) {
            retorno = 100;
        }

        return retorno;
    }

    public double retornaHealth() {
        return (retornaSust() + retornaDiv() + retornaProdut() + retornaRobust() + retornaNiCrea()) / 5.0;

    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        ConsultaCert c = new ConsultaCert();
        //c.saveModel();
        //c.setNamePlatform("Android");
        //c.getRetornaHet();
        //c.getPlatforms();
        //c.getData();
        //c.getRetornaDiversity();
        //int i = c.getCarregamento();
        //System.out.println(c.getCarregamento());
        System.out.println(Math.round(11.111111));
    }
}