import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ResourceResolverFactory
import com.day.cq.wcm.api.Page
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.LoginException;
import javax.jcr.Session;
import javax.jcr.query.QueryManager
import javax.jcr.query.Query
import java.util.regex.*;

// THE PURPOSE OF THIS SCRIPT IS LIST ALL THE TRANSLATEABLE PROPERTY NAMES AND VALUES
// SO LOCAL AGENCIES CAN ASSESS TRANSLATIONS AND PROVIDE FEEDBACK FOR TRANSLATION IMPROVEMENTS.

/* Starting path for the script to begin from */
def path = "/content/trex/de/de"

fieldsToTranslate = ["jcr:title","jcr:description","cq:panelTitle","aboutTrexDescription","aboutTrexTitle","academyHeroCtaLabel","academyHeroTitle","accessibilityLabel","additionDescription","additionPrimaryButtonText","additionSecondaryButtonText","additionTitle","addToCartText","altText","answer","articleIntroDialogMessage","articleSubtitle","articleSummary","attribution","bannerText","bestContentDescription","bestContentTitle","betterContentDescription","betterContentTitle","bottomHeaderText","bottomTitle","buttonText","calculateButtonText","cancelCtaText","caption","cardCtaLabel","cardLabel","cardSubTitle","cardTitle","categoryName","checkboxOneText","checkboxTwoText","checkoutButtonText","checkoutCtaText","chooseCountryLabel","colordescription","colorDescription","colorFinishText","colorOptionsText","colorPatternText","colorPrimaryButtonText","colorSecondaryButtonText","colorTitle","continueText","createAccountCtaText","createAccountText","ctalabel","ctaLinkText","ctaText","ctaTitle","ctaTitles","currencyCode","deckColorIntro","deckColorLabel","deckColorTitle","deckDuoIntro","deckDuoTitle","deckImageLabel","deckingDescription","deckingPrimaryButtonText","deckingSecondaryButtonText","deckingTitle","deckPlanAndSubstructureLabel","deckPlanContinueButtonText","deckPlanIntro","deckPlanTitle","deckRailingDuoLabel","deckSizeContinueButtonText","deckSizeIntro","deckSizeTitle","deckStarterCurrencyText","deckStarterResultsTitle","deckStarterResultsSubTitle","deckSubStructureContinueText","deckSubStructureIntro","deckSubStructureTitle","declarationText","description","descriptionLinkText","detailedResultsDisclaimerText","detailedResultsEmailButtonText","dimensionsDescription","dimensionsSecondaryButtonText","dimensionsPrimaryButtonText","dimensionsTitle","disclaimer","disclaimerText","documentListCtaText","documentListTitle","documentTitle","downloadCtaText","downloadtext","dynamicMediaCtaLabel","dynamicMediaTitle","editItemText","email","emailDetailedResultsButtonText","emailDetailedResultsDescription","emailDetailedResultsTitle","emailfieldLabel","emailMailingListText","emailModalDescription","emailModalTitle","emailModelDesc","emailModelPolicy","emailModelSkip","emailModelSubmit","emailModelTitle","emailPrivacyPolicyText","emailResultsDescription","emailResultsTitle","emailSubject","emptyCartText","estimatedCostDesc","estimatedCostLabel","eyebrow","eyebrowText","featuredProductsTitle","featuredVideoCtaText","firstName","forgotPass","geospecificctatext","geospecifictitle","ghostText","goodContentDescription","goodContentTitle","haveAnAccount","header","headerDescription","heading","headline","headLineText","hotspotBottomLeftDescription","hotspotBottomLeftTitle","hotspotBottomRightDescription","hotspotBottomRightTitle","hotspotTopLeftDescription","hotspotTopLeftTitle","hotspotTopRightDescription","hotspotTopRightTitle","iconDescription","iconMarketingLabel","imageAltText","informativeText","initialContentDisclaimer","initialContentTitle","itemText","jumpToCtaTextFirst","jumpToCtaTextSecond","jumpToText","kpiDescription","kpiTitle","label","largetext","lastName","lengthLabel","lightCalculatorNotes","linkText","linkedText","linkListTitle","linkTitle","listItems","listTitleText","luxuryContentDescription","luxuryContentTitle","mailSubject","mainDescription","materialListHeading","materialListCtaText","mediaGridText","middleHeaderText","mobileTitle","modalCorrespondenceText","modalDescription","modalEmailGhostText","modalMessage","modalSpecialistContactText","modalSubtitle","modalTitle","modalZipcodeGhostText","navTitle","noShippingMethodMessage","noresultfoundtext","ogdesc","ogtitle","orderSummaryText","pageDescription","pageNotFoundText","pageTitle","password","paymentDisclaimer","paymentHeaderText","paymentInfoText","paymentSubmitButtonText","pdfDownloadInfoText","placeholderText","popularSearchText","pretitle","preTitle","primaryButtonText","print","printCtaText","printPageDialogMessage","privacyPolicyText","privacyText","privacyTextForgotClaim","productCategoryTitle","productLineName","productTitle","quantityLabel","question","quickEmailDetailedResultsButtonText","quickEmailDetailedResultsDescription","quickEmailDetailedResultsTitle","quickEmailResultsDescription","quickEmailResultsTitle","quickEstimateRailingDescription","quickEstimateRailingPrimaryButtonText","quickEstimateRailingSecondaryButtonText","quickEstimateRailingTitle","quickResultsColorButtonText","quickResultsColorTitle","quickResultsDisclaimerText","quickResultsEmailButtonText","quickResultsTitle","quote","quoteText","radiobuttonlabel","railingDescription","railingInfoLabel","railingPrimaryButtonText","railingQuestion","railingSecondaryButtonText","railingTitle","readTimeEstimate","readTimeEstimateNote","readTimeText","recentNewsTitle","registerText","relatedArticlesTitle","relatedCarouselTitle","rememberMe","removeText","requiredLabel","resetButtonText","resultText","sampleBGColor","sampleCtaInfo","sampleCtaLabel","sampleSubTitle","sampleTitle","saveForLaterText","searchErrorText","searchPlaceholderText","secondaryButtonText","secondaryCtaText","sectionOneHeader","sectionTitle","selectionSummaryDeckingText","selectionSummaryFasciaText","selectionSummaryFastenersText","selectionSummaryRailingText","selectionSummarySubstructureText","selectionSummaryTitle","selectLabel","seotitle","shippingDisclaimer","shippingInformationTip","shippingInfoText","signInCtaText","signInText","smalltext","standardSizeInfoLabel","stellarBottomText","stepDescription","stepTitle","submissionErrorText","submitButtonText","substructureDescription","substructureInfoLabel","substructurePrimaryButtonText","substructureQuestion","substructureQuickSecondaryButtonText","substructureSecondaryButtonText","substructureTitle","subTitle","subtitle","subtotalInfoText","subTotalText","subtotalText","successfulSubmissionText","successTitle","teamMemberProfile","termsAndConditionsText","teaserTitle","testimonial","text","textBeforeImage","time","title","toolKitCtaLabel","toolKitEditSelectionText","toolKitFormIcon1Subtitle","toolKitFormIcon2Subtitle","toolKitFormIcon3Subtitle","toolKitFormIcon4Subtitle","toolKitFormTitle","topHeaderText","topTitle","totalWattsLabel","trexGoldLevelText","trexPlatinumLevelText","trexProLevelText","videoFilterCtaLabel","viewCartButtonText","viewCartText","widthLabel","widthlabel","yourCartText","zipcode","zipCodeLabel"]

// Inject the ResourceResolverFactory
def resourceResolverFactory = getService("org.apache.sling.api.resource.ResourceResolverFactory")

ResourceResolver resourceResolver = null
 
try {
    // Service user mapping (specify your service user ID)
    Map<String, Object> serviceUserMapping = new HashMap<>()
    serviceUserMapping.put(ResourceResolverFactory.SUBSERVICE, "trex-service-sites")
 
    // Obtain a ResourceResolver using a service user
    resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMapping)
    def rootPage = resourceResolver.getResource(path)
    println("PagePath|PropertyNodePath|PropertyName|PropertyValue")
    traverse(rootPage)
} catch (Exception e) {
    // Handle exceptions
    println("ERROR OCCURRED: "+e);
    /* e.printStackTrace() */
} finally {
    // Always close the resource resolver when done
    if (resourceResolver != null && resourceResolver.isLive()) {
        resourceResolver.close()
    }
}

def traverseNodeProperties(page,node) {
    def properties = node.properties
    while (properties.hasNext()) {
        def property = properties.next()
        def propertyName = property.name
        def propertyPath = property.getPath().replace(page.getPath() + "/jcr:content/", "")
        if (fieldsToTranslate.contains(propertyName)) {
            if (property.isMultiple()) {
                print(page.getPath() + "|" + propertyPath + "|"+propertyName)
                def propertyValues = property.getValues() as String []
                def propertyValue = propertyValues.join("##")
                println("|"+propertyValue.replace("\n", "").replace("\r", "").replace("|", ";"))  
            } else {
                print(page.getPath() + "|" + propertyPath + "|"+propertyName)
                def propertyValue = property.getValue().getString().replace("\n", "").replace("\r", "").replace("|", ";")
                println("|"+propertyValue)   
            }
        }
    }
}

def traverse(respage) {
    if(respage != null) {
        Node node = respage.adaptTo(Node.class)
        if (node != null && node.hasNode("jcr:content")) {
            def jcrContentNode = node.getNode("jcr:content");
            def jcrContentNodePath = jcrContentNode.getPath();
            def isPublished = jcrContentNode.hasProperty("cq:lastReplicated")
            if(isPublished){
                QueryManager queryManager = session.getWorkspace().getQueryManager();
                String queryString = "SELECT * FROM [nt:base] WHERE ISDESCENDANTNODE('$jcrContentNodePath')";
                Query query = queryManager.createQuery(queryString, javax.jcr.query.Query.JCR_SQL2);
                NodeIterator iterator = query.execute().getNodes();
                def jcrNodeResult = traverseNodeProperties(respage,jcrContentNode);

                while (iterator.hasNext()) {
                    Node iterNode = iterator.nextNode();
                    def result = traverseNodeProperties(respage,iterNode);
                }
            }
        }

        Iterator<Resource> iter = respage.listChildren();
        while (iter.hasNext()) {
            traverse(iter.next());
        }
    }
}