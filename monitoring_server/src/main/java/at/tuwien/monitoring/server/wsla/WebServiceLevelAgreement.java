package at.tuwien.monitoring.server.wsla;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.ibm.wsla.WSLAType;

public class WebServiceLevelAgreement {

	private final static Logger logger = Logger.getLogger(WebServiceLevelAgreement.class);

	private String agreementXmlPath;
	private WSLAType wslaType;

	public WebServiceLevelAgreement(String agreementXmlPath) {
		this.agreementXmlPath = agreementXmlPath;
		read();
	}

	private void read() {
		if (!new File(agreementXmlPath).exists()) {
			logger.error("Web service level agreement file not found.");
			return;
		}

		try {
			JAXBContext jc = JAXBContext.newInstance(WSLAType.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			Source source = new StreamSource(agreementXmlPath);
			JAXBElement<WSLAType> root = unmarshaller.unmarshal(source, WSLAType.class);
			wslaType = root.getValue();
			logger.info("Reading of web service level agreement file was successful.");

		} catch (JAXBException e) {
			logger.error("Error reading web service level agreement file.");
		}
	}

	public WSLAType getWSLA() {
		return wslaType;
	}

	public boolean isValid() {
		return wslaType != null;
	}
}
