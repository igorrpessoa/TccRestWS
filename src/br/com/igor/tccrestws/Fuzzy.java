package br.com.igor.tccrestws;

import java.util.List;

import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.UsuarioAtividade;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class Fuzzy {
	
//	public void inicializaFuzzy(List<UsuarioAtividade> listUsuarioAtividade){
	public static Perfil executaFuzzy(UsuarioAtividade ua){
		String filename = "D:/JavaProjects/TccRestWS/src/br/com/igor/tccrestws/area_classif.fcl";
		FIS fis = FIS.load(filename, true);
		Perfil retorno = null;
		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
//			System.exit(1);
		}
		

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);
		
		JFuzzyChart.get().chart(fb);

		Double areaAtiv = 0.0;
		Double freq = 0.0;
		Double satis = 0.0;

//		for(UsuarioAtividade ua :listUsuarioAtividade){
//			for(int i = 0;i<4;i++){
//				switch(i){
//				case 0:
//					areaAtiv = ua.getAtividade().getPerfil().getCulturaCriatividade();
//					break;
//				case 1:
//					areaAtiv = ua.getAtividade().getPerfil().getEducacaoConhecimento();
//					break;
//				case 2:
//					areaAtiv = ua.getAtividade().getPerfil().getHumanaSocial();
//					break;
//				case 3:
//					areaAtiv = ua.getAtividade().getPerfil().getNaturezaSaude();
//					break;
//				}
				freq = ua.getFrequencia();
				satis = ua.getSatisfacao();
				
				// Set inputs
//				fb.setVariable("areaAtiv", areaAtiv);
				fb.setVariable("freq",freq);
				fb.setVariable("satis",satis/10);
		
				// Evaluate1
				fb.evaluate();
		
				// Show output variable's chart
				fb.getVariable("relacao").defuzzify();
				
//				switch(i){
//				case 0:
//					aux.setCulturaCriatividade(fb.getVariable("areaPerfil").getValue());
//					break;
//				case 1:
//					aux.setEducacaoConhecimento(fb.getVariable("areaPerfil").getValue());
//					break;
//				case 2:
//					aux.setHumanaSocial(fb.getVariable("areaPerfil").getValue());
//					break;
//				case 3:
//					aux.setNaturezaSaude(fb.getVariable("areaPerfil").getValue());
//					break;
//				}
//			}
		if(ua.getUsuario().getPerfil() != null && ua.getUsuario().getPerfil().getId() != null && ua.getUsuario().getPerfil().getId() >0){
			retorno = aplicaFuncao(ua.getUsuario().getPerfil(), fb.getVariable("relacao").getValue());
		}else{
			Perfil novoPerfil = ua.getPerfil();
			novoPerfil.setId(0);
			retorno = aplicaFuncao(novoPerfil, fb.getVariable("relacao").getValue());
		}
		//fb.getVariable("areaPerfil").chartDefuzzifier(true);

		
		// Print ruleSet
		//System.out.println(fb);
//		System.out.println("Area Perfil: " + fb.getVariable("areaPerfil").getValue());
		return retorno;
	}
	
	//TODO verificar qual a função em cima do perfil já existente
	public static Perfil aplicaFuncao(Perfil aux,Double relacao){
		aux.setArtistico((relacao+aux.getArtistico())/2);
		aux.setIntelecto((relacao+aux.getIntelecto())/2);
		aux.setSocial((relacao+aux.getSocial())/2);
		aux.setSaude((relacao+aux.getSaude())/2);

		return aux;
	}

}