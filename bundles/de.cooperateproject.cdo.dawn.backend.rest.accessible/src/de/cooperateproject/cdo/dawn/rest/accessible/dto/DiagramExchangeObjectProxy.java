package de.cooperateproject.cdo.dawn.rest.accessible.dto;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class DiagramExchangeObjectProxy implements IDiagramExchangeObject {

	private String value;

	private String id;

	private transient IDiagramExchangeObject proxy;

	/**
	 * The most generic constructor constructing an empty DEO.
	 */
	public DiagramExchangeObjectProxy() {
		proxy = new DiagramExchangeObject();
		value = null;
		id = UUID.randomUUID().toString();
	}

	/**
	 * Constructs an empty object with an id.
	 */
	public DiagramExchangeObjectProxy(String id) {
		this();
		if (id != null) {
			this.id = id;
		}
	}

	/**
	 * Constructs a new object with the given value.
	 *
	 * @param value
	 *            The value of the object to construct.
	 */
	public DiagramExchangeObjectProxy(String id, String value) {
		this(id);
		this.value = value;
	}

	/**
	 * Constructs a new object with a given value that is appended to the given
	 * parent object.
	 *
	 * @param parent
	 *            Parent object to append to.
	 * @param value
	 *            Value of the constructed object.
	 */
	public DiagramExchangeObjectProxy(String id, IDiagramExchangeObject parent, String value) {
		this(id, value);
		parent.appendChild(this);
	}

	@Override
	public void appendChild(IDiagramExchangeObject child) {
		proxy.appendChild(child);
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public ArrayList<IDiagramExchangeObject> getChildren() {
		return proxy.getChildren();
	}

	@Override
	public Optional<IDiagramExchangeObject> getChildByValue(String value) {
		return proxy.getChildByValue(value);
	}

	@Override
	public IDiagramExchangeObject getChildById(String id) {
		return proxy.getChildById(id);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public boolean getRemovable() {
		return proxy.getRemovable();
	}

	@Override
	public boolean getMutable() {
		return proxy.getMutable();
	}

	@Override
	public int getX() {
		return proxy.getX();
	}

	@Override
	public int getY() {
		return proxy.getY();
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setMutable(boolean b) {
		proxy.setMutable(b);
	}

	@Override
	public void setRemovable(boolean b) {
		proxy.setRemovable(b);
	}

	@Override
	public void setX(int x) {
		proxy.setX(x);
	}

	@Override
	public void setY(int y) {
		proxy.setY(y);
	}

	@Override
	public boolean isGroup() {
		return proxy.isGroup();
	}

	@Override
	public boolean isReference() {
		return proxy.isReference();
	}

	@Override
	public IDiagramExchangeObject getReferencedObject() {
		return proxy.getReferencedObject();
	}

}
