package br.com.igor.tccrestws;

import java.io.File;
import java.util.List;

import br.com.igor.tccrestws.entity.Atividade;
import br.com.igor.tccrestws.entity.Perfil;
import br.com.igor.tccrestws.entity.UsuarioAtividade;
import br.com.igor.tccrestws.vo.UsuarioAtividadeVo;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class Fuzzy {
	
//	public void inicializaFuzzy(List<UsuarioAtividade> listUsuarioAtividade){
	public static UsuarioAtividadeVo executaFuzzy(UsuarioAtividadeVo ua){
		String filename = "D:/JavaProjects/TccRestWS/src/br/com/igor/tccrestws/area_classif.fcl";

//		String filename = "C:/apache-tomcat-8.5.28/webapps/TccRestWS/WEB-INF/classes/br/com/igor/tccrestws/area_classif.fcl";
		FIS fis = FIS.load(filename, true);
		UsuarioAtividadeVo retorno = ua;
		Perfil perfil = null;
		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
//			System.exit(1);
		}
		

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);
		
		//JFuzzyChart.get().chart(fb);

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
				//fb.getVariable("relacao").defuzzify();
				
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
		Double relacao = fb.getVariable("relacao").getValue();
		if(ua.getUsuario().getPerfil() != null && ua.getUsuario().getPerfil().getId() != null && ua.getUsuario().getPerfil().getId() >0){
			perfil = aplicaFuncao(ua.getPerfil(),ua.getUsuario().getPerfil(), relacao);
		}else{
			Perfil novoPerfil = ua.getPerfil();
			novoPerfil.setId(0);
			perfil = aplicaFuncao(novoPerfil,relacao);
		}
		retorno.setRelacao(relacao);
		retorno.getUsuario().setPerfil(perfil);
		
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
	//TODO verificar qual a função em cima do perfil já existente
		public static Perfil aplicaFuncao(Perfil atividadePerfil, Perfil novoPerfil,Double relacao){
			novoPerfil.setArtistico(novoPerfil.getArtistico() + (novoPerfil.getArtistico() - atividadePerfil.getArtistico())*(-relacao/100));
			novoPerfil.setIntelecto(novoPerfil.getIntelecto() + (novoPerfil.getIntelecto() - atividadePerfil.getIntelecto())*(-relacao/100));
			novoPerfil.setSocial( novoPerfil.getSocial() + (novoPerfil.getSocial() - atividadePerfil.getSocial())*(-relacao/100));
			novoPerfil.setSaude(novoPerfil.getSaude() + (novoPerfil.getSaude() - atividadePerfil.getSaude())*(-relacao/100));

			return novoPerfil;
		}
}