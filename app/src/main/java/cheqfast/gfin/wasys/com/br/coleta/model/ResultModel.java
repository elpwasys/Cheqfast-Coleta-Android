package cheqfast.gfin.wasys.com.br.coleta.model;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashSet;
import java.util.Set;

public class ResultModel {

	public boolean success;
	public Set<String> messages;
	
	public ResultModel() {
		
	}
	
	public ResultModel(boolean success, String... messages) {
		this.success = success;
		if (ArrayUtils.isNotEmpty(messages)) {
			this.messages = new HashSet<>();
			for (String message : messages) {
				this.messages.add(message);
			}
		}
	}

	public String getMessages() {
		if (CollectionUtils.isNotEmpty(messages)) {
			StringBuilder builder = new StringBuilder();
			for (String message : messages) {
				if (builder.length() > 0) {
					builder.append(", ");
				}
				builder.append(message);
			}
			return String.valueOf(builder);
		}
		return null;
	}
}
