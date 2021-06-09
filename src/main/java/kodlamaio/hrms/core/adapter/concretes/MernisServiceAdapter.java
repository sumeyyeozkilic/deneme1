package kodlamaio.hrms.core.adapter.concretes;



import java.rmi.RemoteException;

import kodlamaio.hrms.core.adapter.abstracts.UserCheckService;
import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

public class MernisServiceAdapter implements UserCheckService{

	@Override
	public boolean checkIfRealPerson(String nationalityId, String firstName, String lastName, String yearOfBirth) {
		KPSPublicSoapProxy client = new KPSPublicSoapProxy();
		boolean result=false;
		
        try {
			
			result = client.TCKimlikNoDogrula(Long.parseLong(nationalityId),firstName.toUpperCase(),lastName.toUpperCase(),Integer.parseInt(yearOfBirth));
			
		}catch (RemoteException e) {
			
			return result;
		
		}

		return result; 
	}
	

	
		
	/*	try {
			check=client.TCKimlikNoDogrula(
						Long.parseLong(candidate.getNationalityId()), 
						candidate.getFirstName(), 
						candidate.getLastName(), 
						candidate.getDateOfBirth());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return check; */
	}


