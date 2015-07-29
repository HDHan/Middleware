package kr.ac.ajou.lazybones.repos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import kr.ac.ajou.lazybones.managers.DynamoDBManager;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;

public class NodeRepository {
	static Node nodeDB;
	
	static DynamoDBMapper mapper;
	
	public NodeRepository () {
		nodeDB = new Node();
		mapper = DynamoDBManager.getMapper();
	}
	
	public NodeRepository (NodeEntity ent) {
		nodeDB = new Node(ent.getNID(), ent.getSerialNumber(), ent.getOwner(), ent.getProductName(), ent.getNodeName());
		mapper = DynamoDBManager.getMapper();
	}
	
	public void createNodeItems(NodeEntity ent) {
		createNodeItems(nodeDB, ent.getNID(), ent.getSerialNumber(), ent.getOwner(), ent.getProductName(), ent.getNodeName());
	}

	private void createNodeItems(Node nodeDB, Integer nid, String sn, String owner, String pn, String name) {
		nodeDB.setNodeID(nid);
		nodeDB.setSerialNumber(sn);
		nodeDB.setOwner(owner);		
		nodeDB.setProductName(pn);
		nodeDB.setNodeName(name);
		
		mapper.save(nodeDB);
		
		Integer hashKey = nid;
		String rangeKey = sn;
		Node nodeKey = new Node();
		nodeKey.setNodeID(hashKey);
		
		Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
		DynamoDBQueryExpression<Node> queryExpression = new DynamoDBQueryExpression<Node>().withHashKeyValues(nodeKey)
				.withRangeKeyCondition("SerialNumber", rangeKeyCondition);
		
		List<Node> latestNode = mapper.query(Node.class, queryExpression);
        
		System.out.println("Item created: ");
		for(Node n : latestNode) {
			System.out.format("NodeID=%s, SerialNumber=%s, Owner=%s, Product Name=%s, Node Name=%s\n", n.getNodeID(), 
					n.getSerialNumber(), n.getOwner(), n.getProductName(), n.getNodeName());
		}
	}
	
	public void printNodeItems(NodeEntity ent) {
		printNodeItems(ent.getNID(), ent.getSerialNumber());
	}
	
	private void printNodeItems(Integer nid, String sn) {
		Integer hashKey = nid;
		String rangeKey = sn;
		Node nodeKey = new Node();
		nodeKey.setNodeID(hashKey);
		List<Node> latestNode;
		
		if(sn != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<Node> queryExpression = new DynamoDBQueryExpression<Node>().withHashKeyValues(nodeKey)
				.withRangeKeyCondition("SerialNumber", rangeKeyCondition);
			latestNode = mapper.query(Node.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<Node> queryExpression = new DynamoDBQueryExpression<Node>().withHashKeyValues(nodeKey);
			latestNode = mapper.query(Node.class, queryExpression);
		}
		
		for(Node node : latestNode) {
			System.out.format("NodeID=%s, SerialNumber=%s, Owner=%s, Product Name=%s, Node Name=%s\n", node.getNodeID(), 
					node.getSerialNumber(), node.getOwner(), node.getProductName(), node.getNodeName());
		}
	}
	
	public NodeEntity findNode(NodeEntity ent) {
		return findNode(ent.getNID(), ent.getSerialNumber());
	}
	
	private NodeEntity findNode(Integer nid, String sn) {
		Integer hashKey = nid;
		String rangeKey = sn;
		Node nodeKey = new Node();
		nodeKey.setNodeID(hashKey);
		List<Node> latestNode;
		
		if(sn != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<Node> queryExpression = new DynamoDBQueryExpression<Node>().withHashKeyValues(nodeKey)
				.withRangeKeyCondition("SerialNumber", rangeKeyCondition);
			latestNode = mapper.query(Node.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<Node> queryExpression = new DynamoDBQueryExpression<Node>().withHashKeyValues(nodeKey);
			latestNode = mapper.query(Node.class, queryExpression);
		}
		
		NodeEntity ret = new NodeEntity();
		for(Node n : latestNode) {
			ret.setNID(n.getNodeID());
			ret.setSerialNumber(n.getSerialNumber());
			ret.setOwner(n.getOwner());
			ret.setProductName(n.getProductName());
			ret.setNodeName(n.getNodeName());
		}
		
		return ret;
	}

	public List<NodeEntity> findNodesByOwner(String owner) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("Owner", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(owner)));
		List<Node> scanResult = mapper.scan(Node.class, scanExpression);
		
		List<NodeEntity> list = new ArrayList<>();
		
		for(Node node : scanResult) {
			NodeEntity ret = new NodeEntity();
			
			ret.setNID(node.getNodeID());
			ret.setSerialNumber(node.getSerialNumber());
			ret.setOwner(node.getOwner());
			ret.setProductName(node.getProductName());
			ret.setNodeName(node.getNodeName());
			
			list.add(ret);
		}
		
		return list;
	}
	
	public NodeEntity findNodebyNodeID(Integer id) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("NodeID", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withN(id.toString())));
		List<Node> scanResult = mapper.scan(Node.class, scanExpression);
		if(scanResult.isEmpty())
			return null;
		
		NodeEntity ret = new NodeEntity();
		for(Node node : scanResult) {
			ret.setNID(node.getNodeID());
			ret.setSerialNumber(node.getSerialNumber());
			ret.setOwner(node.getOwner());
			ret.setProductName(node.getProductName());
			ret.setNodeName(node.getNodeName());
		}
		
		return ret;
	}
	
	public NodeEntity findNodeBySerialNumber(String sn) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("SerialNumber", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(sn)));
		List<Node> scanResult = mapper.scan(Node.class, scanExpression);
		
		if(scanResult.isEmpty())
			return null;
		
		NodeEntity ret = new NodeEntity();
		for(Node node : scanResult) {
			ret.setNID(node.getNodeID());
			ret.setSerialNumber(node.getSerialNumber());
			ret.setOwner(node.getOwner());
			ret.setProductName(node.getProductName());
			ret.setNodeName(node.getNodeName());
		}
		
		return ret;
	}
	
	public void updateNodeItem(NodeEntity ent) {
		updateNodeItem(ent.getNID(), ent.getSerialNumber(), ent.getOwner(), ent.getSerialNumber(), ent.getProductName());
	}
	
	private void updateNodeItem(Integer nid, String sn, String owner, String pn, String name) {
		Node itemRetrieved;
		
		if(sn != null) {
			itemRetrieved = mapper.load(Node.class, nid, sn);
			itemRetrieved.setSerialNumber(sn);
		}
		else
			itemRetrieved = mapper.load(Node.class, nid);
		
		itemRetrieved.setOwner(owner);
		itemRetrieved.setProductName(pn);
		itemRetrieved.setNodeName(name);
        System.out.println("Item updated: ");
        System.out.format("NodeID=%s, SerialNumber=%s, Owner=%s, Product Name=%s, Node Name=%s\n", itemRetrieved.getNodeID(),
        		itemRetrieved.getSerialNumber(), itemRetrieved.getOwner(), itemRetrieved.getProductName(), itemRetrieved.getNodeName());
	}
	
	/*
	public void findUpdatedNodeItems() {
		findUpdatedNodeItems(node.getSerialNumber());
	}
	
	public void findUpdatedNodeItems(String nid) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		Node updatedItem = mapper.load(Node.class, nid, config);
		
        System.out.println("Retrieved the previously updated item: ");
        System.out.println(updatedItem);
	}
	*/
	
	public void deleteNodeItem(NodeEntity ent) {
		deleteNodeItem(ent.getNID(), ent.getSerialNumber());
	}
	
	private void deleteNodeItem(Integer nid, String sn) {
        DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		Node updatedItem;
		Node deletedItem;
		
		if(sn != null) {
			updatedItem = mapper.load(Node.class, nid, sn, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(Node.class, updatedItem.getNodeID(), updatedItem.getSerialNumber(), config);
		}
		else {
			updatedItem = mapper.load(Node.class, sn, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(Node.class, updatedItem.getNodeID(), config);
		}

        if (deletedItem == null)
            System.out.println("Done - The item is deleted.");
        else
        	System.out.println("Fail - The item is still remained.");
	}
    
    @DynamoDBTable(tableName="Node")
    public static class Node {
    	private Integer NodeID;
    	private String SerialNumber;
        private String Owner;        
        private String ProductName;
        private String NodeName;
        
        public Node() {
        	
        }
        
        public Node(Integer nid, String sn, String owner, String pn, String name) {
			// TODO Auto-generated constructor stub
        	this.NodeID = nid;
        	this.SerialNumber = sn;
        	this.Owner = owner;        	
        	this.ProductName = pn;
        	this.NodeName = name;
		}
        
        @DynamoDBHashKey(attributeName="NodeID")
        public Integer getNodeID() { return NodeID; }
        public void setNodeID(Integer NodeID) { this.NodeID = NodeID; }
        
        @DynamoDBRangeKey(attributeName="SerialNumber")
        public String getSerialNumber() { return SerialNumber; }
        public void setSerialNumber(String SerialNumber) { this.SerialNumber = SerialNumber; }
        
        @DynamoDBAttribute(attributeName="Owner")
        public String getOwner() { return Owner; }    
        public void setOwner(String Owner) { this.Owner = Owner; }
                        
        @DynamoDBAttribute(attributeName="ProductName")
        public String getProductName() { return ProductName; }
        public void setProductName(String ProductName) { this.ProductName = ProductName; }
        
        @DynamoDBAttribute(attributeName="NodeName")
        public String getNodeName() { return NodeName; }
        public void setNodeName(String NodeName) { this.NodeName = NodeName; }
    }
}